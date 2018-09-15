import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	Stage stage;
	Scene scene;
	static Pane gameRoot;
	static BorderPane menuRoot, gameOverRoot, howRoot, pauseRoot;

	static Player player;
	Earth earth;

	static VBox exitRoot, menuBox;

	private long lastHitTime = 0;
	private long timeOfLastProjectile = 0;
	private boolean gameplay = false;

	Button yesExit = new Button("Yes");
	Button noExit = new Button("No");

	static Rectangle healthBarOutline, actualHealth, lostHealth; //for player
	static Rectangle earthHealthBar, earthActualHealth, earthLostHealth; //for earth
	Label scoreLabel;
        Text earthText;
	VBox health, coinAndScore;

	private List<Projectile> projectiles = new ArrayList();
	private List<Projectile> projectilesToRemove = new ArrayList();

	private List<Debris> debris = new ArrayList();
	private List<Debris> debrisToRemove = new ArrayList();

	private List<Astronaut> astronauts = new ArrayList();
	private List<Astronaut> astronautsToRemove = new ArrayList();

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
                menuRoot.setId("menu");
                
                final Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        int s = INDEFINITE;
                        AudioClip audio = new AudioClip(getClass().getResource("sprites/gamemusic.wav").toExternalForm());
                        audio.setVolume(0.5f);
                        audio.setCycleCount(s);
                        audio.play();
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.start();
                
                /*AudioClip note = new AudioClip(this.getClass().getResource("sprites/gamemusic.wav").toString());
                note.play();*/

		createGameRoot();
		createGameOverRoot();
		createHowRoot();
                createPauseRoot();
		scene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
		scene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

		Button startBtn = new Button("Start");
		startBtn.setOnAction(e -> {
			stage.getScene().setRoot(gameRoot);
			newGame();
		});
		Button playBtn = new Button("How to Play");
		playBtn.setOnAction(e -> {
			stage.getScene().setRoot(howRoot);
		});
		Button exitBtn = new Button("Quit");
		exitBtn.setOnAction(e -> {
			stage.getScene().setRoot(exitRoot);

			yesExit.setOnAction(eY -> {
				Platform.exit();
				gameplay = false;
				clearAll();
			});
			noExit.setOnAction(eN -> {
				stage.getScene().setRoot(gameOverRoot);
			});
		});
		menuBox = new VBox(10);
		menuBox.getChildren().addAll(startBtn, playBtn, exitBtn);
		menuRoot.setCenter(menuBox);
		menuBox.setAlignment(Pos.CENTER);
		Text title = new Text("EARTH PROTECTOR");
                title.setFill(Color.WHITE);
		title.setFont(Font.font("Arial", 50));
		menuRoot.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(100));

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update(stage);
			}
		};
		timer.start();

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

        long pauseTime = 0;
	public void update(Stage stage) {
            long timeNow = System.currentTimeMillis();
            long time = timeNow - pauseTime;
		if (gameplay) {
			if (player.getHealth() == 0 || earth.getHealth() == 0) {
				Text gameOver = new Text("Game Over \n Score:  " + player.getScore());
                                gameOver.setFill(Color.WHITE);
				gameOver.setFont(Font.font("Arial", 50));
				gameOverRoot.setTop(gameOver);
				BorderPane.setAlignment(gameOver, Pos.CENTER);
				BorderPane.setMargin(gameOver, new Insets(100));
				stage.getScene().setRoot(gameOverRoot);
				gameplay = false;
			}
			if (isPressed(KeyCode.RIGHT)) {
				player.moveClockwise(true, 25);
			}
			if (isPressed(KeyCode.LEFT)) {
				player.moveClockwise(false, 25);
			}
			if (isPressed(KeyCode.SPACE)) {
				shoot();
			}
                        if (player.getScore() < 50) {
                            if (Math.random() < 0.005) {
				createDebris();
                            }
                            if (Math.random() < 0.005) {
				createAstronaut();
                            }
                        } else if (player.getScore() < 100 && player.getScore() >= 50) {
                            if (Math.random() < 0.01) {
				createDebris();
                            }
                            if (Math.random() < 0.005) {
				createAstronaut();
                            }
                        } else {
                            if (Math.random() < 0.02) {
				createDebris();
                            }
                            if (Math.random() < 0.01) {
				createAstronaut();
                            }
                        }
			for (Projectile proj : projectiles) {
				updateProjectiles(proj);
			}
			for (Debris debri : debris) {
				updateDebris(debri);
			}
			for (Astronaut astro : astronauts) {
				updateAstronaut(astro);
			}
                        
                        if (time < 0 || time > 250) {
                            if (isPressed(KeyCode.ESCAPE)) {
                                gameplay = false;
                                stage.getScene().setRoot(pauseRoot);
                            }
                            pauseTime = timeNow;
                        }
			clearLists();
		}
                else {
                    if (time < 0 || time > 250) {
                        if (isPressed(KeyCode.ESCAPE)) {
                                gameplay = true;
                                stage.getScene().setRoot(gameRoot);
                        }
                        pauseTime = timeNow;
                    }
                }
	}

	public void createDebris() {
		double randX = 0;
		double randY = 0;
		int scenerios = (int) (Math.random() * 4);
		if (scenerios == 0) {
			randX = -160;
			randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
		}
		if (scenerios == 1) {
			randX = screenSize.getWidth();
			randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
		}
		if (scenerios == 2) {
			randY = -160;
			randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
		}
		if (scenerios == 3) {
			randY = screenSize.getHeight();
			randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
		}

		Debris newdebris = new Debris("file:src/sprites/Asteroid.png", randX, randY, 3, 1, 50, 50, screenSize);
		gameRoot.getChildren().add(newdebris);
		debris.add(newdebris);
	}

	public void updateDebris(Debris debri) {
		debri.move(screenSize);
		long timeNow = System.currentTimeMillis();
		long time = timeNow - lastHitTime;
		if (debri.isColliding(player)) {
                    if (time < 0 || time > 500) {
                        player.hit();
                        playerReceiveHit();
                        lastHitTime = timeNow;
                    }
                    debri.setAlive(false);
		}
		if (debri.isEarthColliding(earth)) {
			earth.hit();
			gameRoot.getChildren().remove(earthActualHealth);
			earthActualHealth = new Rectangle(356, 21, earth.getHealth() * 108, 19);
			earthActualHealth.setFill(Color.GREEN);
			gameRoot.getChildren().add(earthActualHealth);
                        earthText.toFront();
			debri.setAlive(false);
		}
		if (!debri.isAlive()) {
			gameRoot.getChildren().remove(debri);
			debrisToRemove.add(debri);
		}
	}

	public void createAstronaut() {
		double randX = 0;
		double randY = 0;
		int scenerios = (int) (Math.random() * 4);
		if (scenerios == 0) {
			randX = -160;
			randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
		}
		if (scenerios == 1) {
			randX = screenSize.getWidth();
			randY = (Math.random() * (screenSize.getHeight() + 160) - 160);
		}
		if (scenerios == 2) {
			randY = -160;
			randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
		}
		if (scenerios == 3) {
			randY = screenSize.getHeight();
			randX = (Math.random() * (screenSize.getWidth() + 160) - 160);
		}

		Astronaut astronaut = new Astronaut("file:src/sprites/Astronaut.png", randX, randY, 3, 1, 50, 50, screenSize);
		gameRoot.getChildren().addAll(astronaut, astronaut.middle);
		astronauts.add(astronaut);
	}

	public void updateAstronaut(Astronaut astro) {
		astro.move(screenSize);
		if (astro.isColliding(player)) {
			player.increaseScore();
			astro.setAlive(false);
			scoreLabel.setText("Score: " + player.getScore());
			scoreLabel.setTextFill(Color.WHITE);
		}
		if (astro.isEarthColliding(earth)) {
			astro.setAlive(false);
		}
		if (!astro.isAlive()) {
			gameRoot.getChildren().remove(astro);
			astronautsToRemove.add(astro);
                        astro.updateHit();
                        astro.middle.toFront();
		}
	}
        
        /*long spawnTime = 0;
        public void updateExplosions(Explosion explo) {
            explo.move();
            long timeNow = System.currentTimeMillis();
            long time = timeNow - spawnTime;
            if (time < 0 || time > 900) {
                gameRoot.getChildren().remove(explo);
		explosToRemove.add(explo);
                spawnTime = timeNow;
            }
        }*/

	public boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}

	public void updateProjectiles(Projectile projectile) {
		projectile.move(earth);
		for (Debris debris : debris) {
			if (projectile.enemyColliding(debris)) {
				projectile.setAlive(false);
				debris.setAlive(false);
				player.increaseScore();
				scoreLabel.setText("Score: " + player.getScore());
				scoreLabel.setTextFill(Color.WHITE);
			}
		}
		for (Astronaut astro : astronauts) {
			if (projectile.astroColliding(astro)) {
				projectile.setAlive(false);
				astro.setAlive(false);
				player.decreaseScore();
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

	public void clearLists() {
		projectiles.removeAll(projectilesToRemove);
		debris.removeAll(debrisToRemove);
		astronauts.removeAll(astronautsToRemove);
		projectilesToRemove.clear();
		debrisToRemove.clear();
		astronautsToRemove.clear();
	}

	public void playerReceiveHit() {
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
		if (time < 0 || time > 250) {
			Projectile projectile = new Projectile("file:src/sprites/HomingShot.png", player.getX(), player.getY(), 24, 10);
			projectile.setVelocityX(5);
			projectile.setVelocityY(5);
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
		coinAndScore.toBack();

		//For earth stuff
		earthHealthBar = new Rectangle(355, 20, 541, 20);
		earthHealthBar.setFill(Color.TRANSPARENT);
		earthHealthBar.setStroke(Color.BLACK);
		earthLostHealth = new Rectangle(356, 21, 540, 19);
		earthLostHealth.setFill(Color.RED);
		earthActualHealth = new Rectangle(356, 21, 540, 19);
		earthActualHealth.setFill(Color.GREEN);
	}

	public void createGameOverRoot() {
		VBox gameOverBox = addGameOverButtons(stage);
		gameOverBox.setAlignment(Pos.TOP_CENTER);
		gameOverRoot = new BorderPane();
		gameOverRoot.setCenter(gameOverBox);
		exitRoot = new VBox(20);
		Text exitString = new Text("Are you sure you want to exit?");
                exitString.setFill(Color.WHITE);
		exitString.setFont(Font.font("Arial", 25));
		HBox exitButtons = new HBox(10);
		exitButtons.getChildren().addAll(yesExit, noExit);
		exitButtons.setAlignment(Pos.CENTER);
		exitRoot.getChildren().addAll(exitString, exitButtons);
		exitRoot.setAlignment(Pos.CENTER);
                gameOverRoot.setId("menu");
                exitRoot.setId("menu");
	}

	public VBox addGameOverButtons(Stage stage) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20));
		vbox.setSpacing(10);

		Button exitBtn = new Button("QUIT");
		exitBtn.setOnAction(e -> {
			stage.getScene().setRoot(exitRoot);

			yesExit.setOnAction(eY -> {
				Platform.exit();
				gameplay = false;
				clearAll();
			});
			noExit.setOnAction(eN -> {
				stage.getScene().setRoot(gameOverRoot);
			});
		});

		Button newBtn = new Button("NEW GAME");
		newBtn.setOnAction(e -> {
			stage.getScene().setRoot(gameRoot);
			clearAll();
			newGame();
		});
                
                Button backToMenu = new Button("BACK TO MENU");
                backToMenu.setOnAction(e -> {
			stage.getScene().setRoot(menuRoot);
			clearAll();
			newGame();
		});

		vbox.getChildren().addAll(exitBtn, newBtn, backToMenu);
		return vbox;
	}
        
        boolean alreadyStarted = false;
    public void newGame() {
        if (alreadyStarted) {
            gameRoot.getChildren().removeAll(player, earth, health, healthBarOutline, lostHealth,
                            actualHealth, coinAndScore, earthActualHealth, earthHealthBar, earthLostHealth);
        }
            player = new Player("file:src/sprites/player.png", 5, 25, 25, (int) screenSize.getWidth(), (int) screenSize.getHeight());
            earth = new Earth("file:src/sprites/Earth.png", 5, 160, 160, (int) screenSize.getWidth(), (int) screenSize.getHeight());
            gameRoot.setId("backgroundgame");
            actualHealth = new Rectangle(screenSize.getWidth() - 120, 10, 100, 22);
            actualHealth.setFill(Color.web("#00F32C"));
            earthActualHealth = new Rectangle(356, 21, 540, 19);
            earthActualHealth.setFill(Color.GREEN);
            earthText = new Text("EARTH");
            earthText.setFont(Font.font("Arial", 25));
            earthText.setTranslateX(590);
            earthText.setTranslateY(50);
            earthText.setFill(Color.WHITE);
            gameRoot.getChildren().addAll(player, earth, health, healthBarOutline, lostHealth, earthText,
                            actualHealth, coinAndScore, earthActualHealth, earthHealthBar, earthLostHealth);
            coinAndScore.toFront();
            scoreLabel.toFront();
            health.toFront();
            healthBarOutline.toFront();
            lostHealth.toFront();
            actualHealth.toFront();
            earthHealthBar.toFront();
            earthLostHealth.toFront();
            earthActualHealth.toFront();
            earthText.toFront();
            gameplay = true;
            alreadyStarted = true;
            
            //Collision rectangle additions
            gameRoot.getChildren().addAll(earth.middle);
            
    }
        
	public void createHowRoot() {
		Text title = new Text("How To Play");
		title.setFont(Font.font("Arial", 40));
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		howRoot = new BorderPane();
		VBox howBox = createHowBox();
		howBox.setAlignment(Pos.CENTER);
		howRoot.setCenter(howBox);
		howRoot.setTop(title);
		BorderPane.setAlignment(howBox,Pos.CENTER);
		Button backBtn = new Button("BACK TO MENU");
		backBtn.setOnAction(e -> {
			stage.getScene().setRoot(menuRoot);
			BorderPane.setAlignment(menuBox, Pos.CENTER);
		});
		howRoot.setBottom(backBtn);
		BorderPane.setAlignment(backBtn, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(backBtn, new Insets(50));
                howRoot.setId("menu");
	}
	
	public VBox createHowBox(){
		VBox controls = new VBox();
		Image arrowImage = new Image("file:src/sprites/ArrowKeys.png");
		ImageView arrowIV = new ImageView(arrowImage);
		Text arrowText = new Text("Use the arrowkeys to move the player");
                arrowText.setFill(Color.WHITE);
		arrowText.setFont(Font.font("Arial", 20));
		HBox arrowBox = new HBox(arrowIV, arrowText);
		arrowBox.setAlignment(Pos.CENTER);
		Image barImage = new Image("file:src/sprites/SpaceBar.png");
		ImageView barIV = new ImageView(barImage);
		Text barText = new Text("Use the spacebar to shoot");
		barText.setFont(Font.font("Arial", 20));
                barText.setFill(Color.WHITE);
		HBox barBox = new HBox(barIV, barText);
		barBox.setAlignment(Pos.CENTER);
		Image asteroidImage = new Image("file:src/sprites/ControlAsteroid.png");
		ImageView asteroidIV = new ImageView(asteroidImage);
		Text asteroidText = new Text("\n\nHit the asteroids before they reach the Earth");
		asteroidText.setFont(Font.font("Arial", 20));
                asteroidText.setFill(Color.WHITE);
		HBox asteroidBox = new HBox(asteroidIV, asteroidText);
		asteroidBox.setAlignment(Pos.CENTER);
		Image astroImage = new Image("file:src/sprites/ControlAstronaut.png");
		ImageView astroIV = new ImageView(astroImage);
		Text astroText = new Text("\n\nSave the astronauts by catching them");
		astroText.setFont(Font.font("Arial", 20));
                astroText.setFill(Color.WHITE);
		HBox astroBox = new HBox(astroIV, astroText);
		astroBox.setAlignment(Pos.CENTER);
		controls.getChildren().addAll(arrowBox, barBox, asteroidBox, astroBox);
		controls.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(controls, Pos.CENTER);
		return controls;
	}

	public void createPauseRoot() {
		pauseRoot = new BorderPane();
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(20));
		vbox.setSpacing(10);

		Button exitBtn = new Button("QUIT");
		exitBtn.setOnAction(e -> {
			stage.getScene().setRoot(exitRoot);

			yesExit.setOnAction(eY -> {
				Platform.exit();
				gameplay = false;
				clearAll();
			});
			noExit.setOnAction(eN -> {
				stage.getScene().setRoot(gameOverRoot);
			});
		});

		Button newBtn = new Button("NEW GAME");
		newBtn.setOnAction(e -> {
			stage.getScene().setRoot(gameRoot);
			clearAll();
			newGame();
		});

		Button backToMenu = new Button("BACK TO MENU");
		backToMenu.setOnAction(e -> {
			stage.getScene().setRoot(menuRoot);
			gameplay = false;
			clearAll();
			newGame();
		});

		Button resume = new Button("RESUME");
		resume.setOnAction(e -> {
			stage.getScene().setRoot(gameRoot);
			gameplay = true;
		});

		vbox.getChildren().addAll(resume, backToMenu, newBtn, exitBtn);

		Text title = new Text("PAUSE MENU");
		title.setFont(Font.font("Arial", 50));

		vbox.setAlignment(Pos.CENTER);
		pauseRoot.setTop(title);
		pauseRoot.setCenter(vbox);
		BorderPane.setAlignment(vbox, Pos.TOP_CENTER);

		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(100));
	}

	public void clearAll() {
		projectiles.clear();
		projectilesToRemove.clear();
		debrisToRemove.clear();
		debris.clear();
		scoreLabel.setText("Score: ");
		gameRoot.getChildren().clear();
	}
}