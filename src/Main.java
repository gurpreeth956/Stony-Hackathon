import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;
    Scene scene;
    static Pane gameRoot;
    static BorderPane menuRoot;

    static Player player;
    Earth earth;
    
    private long timeOfLastProjectile = 0;

    static Rectangle healthBarOutline, actualHealth, lostHealth;
    Label scoreLabel;
    VBox health, coinAndScore;

    private List<Projectile> projectiles = new ArrayList();
    private List<Projectile> projectilesToRemove = new ArrayList();

    private List<Debris> debris = new ArrayList();
    private List<Debris> debrisToRemove = new ArrayList();

    private final HashMap<KeyCode, Boolean> keys = new HashMap();
    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            stage = primaryStage;
            menuRoot = new BorderPane();
            scene = new Scene(menuRoot, screenSize.getWidth(), screenSize.getHeight());
            scene.getStylesheets().addAll(this.getClass().getResource("Design.css").toExternalForm());

            createGameRoot();
            scene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
            scene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

            Button bttn = new Button("Start");
            bttn.setOnAction(e -> {
                    stage.getScene().setRoot(gameRoot);
                    newGame();
            });

            AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                            update(stage);
                    }
            };
            timer.start();

            //adding to roots
            menuRoot.setCenter(bttn);

            //gameRoot.getChildren().addAll(player);
            stage.setTitle("The Elimination of Space Pollution");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setResizable(false);
            stage.show();
    }

    public void update(Stage stage) {
            if (isPressed(KeyCode.RIGHT)) {
                    player.moveClockwise(true, 25);
            }
            if (isPressed(KeyCode.LEFT)) {
                    player.moveClockwise(false, 25);
            }
            if (isPressed(KeyCode.SPACE)) {
                    shoot();
            }
            if(Math.random() < 0.01)
                createDebris();
            updateProjectiles();
            updateDebris();
            clearLists();
    }
    
    
    public void createDebris(){
        double randX = 0;
        double randY = 0;
        int scenerios = (int)(Math.random()*4);
        if(scenerios == 0){
            randX = -160;
            randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
        }
        if(scenerios == 1){
            randX = screenSize.getWidth();
            randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
        }
        if(scenerios == 2){
            randY = -160;
            randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
        }
        if(scenerios == 3){
            randY = screenSize.getHeight();
            randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
        }

        Debris newdebris = new Debris("file:src/sprites/rocket.png", randX, randY, 3, 1, 50,50, screenSize);
        gameRoot.getChildren().add(newdebris);
        debris.add(newdebris);
    }

    public void updateDebris(){
            for(Debris debri:debris){
                debri.move(screenSize);
                    if(debri.isColliding(player)){
                            player.hit();
                            playerReceiveHit();
                            debri.setAlive(false);
                    }
                    if(debri.isEarthColliding(earth)){
                        earth.hit();
                        debri.setAlive(false);
                    }
                    if(!debri.isAlive()){
                            gameRoot.getChildren().remove(debri);
                            debrisToRemove.add(debri);
                    }
            }
    }

    public boolean isPressed(KeyCode key) {
            return keys.getOrDefault(key, false);
    }

	public void updateProjectiles() {
		for (Projectile projectile : projectiles) {
			projectile.move(earth);
			for (Debris debris : debris) {
				if (projectile.enemyColliding(debris)) {
					projectile.setAlive(false);
					debris.setAlive(false);
					scoreLabel.setText("Score: " + player.getScore());
					scoreLabel.setTextFill(Color.WHITE);
				}
			}
			if (projectile.getTranslateX() <= 0 || projectile.getTranslateX() >= scene.getWidth()) {
				projectile.setAlive(false);
			} else if (projectile.getTranslateY() <= 0 || projectile.getTranslateY() >= scene.getHeight()) {
				projectile.setAlive(false);
			}
			if (!projectile.isAlive()) {
				gameRoot.getChildren().remove(projectile);
				projectilesToRemove.add(projectile);
			}
		}
	}

	public void clearLists() {
		projectiles.remove(projectilesToRemove);
		debris.remove(debrisToRemove);
		projectilesToRemove.clear();
		debrisToRemove.clear();
	}

	public static void playerReceiveHit() {
		//determines which bar takes damage
		gameRoot.getChildren().remove(actualHealth);
		actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, player.getHealth() * 20, 22);
		actualHealth.setFill(Color.web("#00F32C"));
		gameRoot.getChildren().add(actualHealth);
		actualHealth.toFront();

	}

	public void shoot() {
		long timeNow = System.currentTimeMillis();
		long time = timeNow - timeOfLastProjectile;
		if (time < 0 || time > 500) {
			Projectile projectile = new Projectile("file:src/sprites/Shot.png", player.getX(), player.getY(), 12, 12);
			projectile.setVelocityX(5);
			projectile.setVelocityY(5);//player.getVelocity().normalize().multiply(5));
			//projectile.setTranslateX(player.getTranslateX());
			//projectile.setTranslateY(player.getTranslateY());
			projectiles.add(projectile);
			gameRoot.getChildren().add(projectile);
			timeOfLastProjectile = timeNow;
		}
	}

	public void createGameRoot() {
		gameRoot = new Pane();
		gameRoot.setId("backgroundgame");
		Label healthLabel = new Label("Health: ");
		healthLabel.setFont(new Font("Arial", 20));
		healthLabel.setTextFill(Color.WHITE);
		healthLabel.toFront();
		healthBarOutline = new Rectangle(screenSize.getWidth() - 121, 9, 102, 22);
		healthBarOutline.setFill(Color.TRANSPARENT);
		healthBarOutline.setStroke(Color.BLACK);
		lostHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
		lostHealth.setFill(Color.RED);
		actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
		actualHealth.setFill(Color.web("#00F32C"));
		health = new VBox(10);
		health.getChildren().addAll(healthLabel);
		health.setTranslateX(screenSize.getWidth() - 200);
		health.setTranslateY(10);
		scoreLabel = new Label("Score: ");
		scoreLabel.setFont(new Font("Arial", 20));
		scoreLabel.setTextFill(Color.WHITE);
		coinAndScore = new VBox(10);
		coinAndScore.getChildren().addAll(scoreLabel);
		coinAndScore.setTranslateX(10);
		coinAndScore.setTranslateY(10);
	}

	public void newGame() {
		player = new Player("file:src/sprites/player.png", 5, 33, 33, (int) screenSize.getWidth(), (int) screenSize.getHeight());
		earth = new Earth("file:src/sprites/EarthM.png", 5, 160, 160, (int) screenSize.getWidth(), (int) screenSize.getHeight());
		gameRoot.setId("backgroundgame");
		gameRoot.getChildren().addAll(earth);
		actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
		actualHealth.setFill(Color.web("#00F32C"));
		gameRoot.getChildren().addAll(player, health, healthBarOutline, lostHealth,
				actualHealth, coinAndScore);
		coinAndScore.toFront();
		scoreLabel.toFront();
		health.toFront();
		healthBarOutline.toFront();
		lostHealth.toFront();
		actualHealth.toFront();
	}
}
