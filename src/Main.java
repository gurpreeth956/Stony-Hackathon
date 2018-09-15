
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;
    Scene scene;
    static Pane gameRoot;
    static BorderPane menuRoot;

    Player player;
    Earth earth;

    private final HashMap<KeyCode, Boolean> keys = new HashMap();
    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        gameRoot = new Pane();
        menuRoot = new BorderPane();
        scene = new Scene(menuRoot, screenSize.getWidth(), screenSize.getHeight());
        scene.getStylesheets().addAll(this.getClass().getResource("Design.css").toExternalForm());

        createGameRoot();
        scene.setOnKeyPressed(e -> keys.put(e.getCode(), true));
        scene.setOnKeyReleased(e -> keys.put(e.getCode(), false));

        Button bttn = new Button("Start");
        bttn.setOnAction(e -> {
                stage.getScene().setRoot(gameRoot);
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
                player.moveClockwise(true);
        }
        if (isPressed(KeyCode.LEFT)) {
                player.moveClockwise(false);
        }
        if (isPressed(KeyCode.SPACE)) {
                shoot();
        }
    }

    public void shoot() {

    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public void createGameRoot() {
        player = new Player((int) screenSize.getWidth(), (int) screenSize.getHeight());
        earth = new Earth("file:src/sprites/EarthM.png", 5, 160, 160, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        Projectile rocket = new Projectile("file:src/sprites/rocket.png", 30,30,50,50, 5);
        gameRoot.setId("backgroundgame");
        gameRoot.getChildren().addAll(earth,rocket);
    }
}
