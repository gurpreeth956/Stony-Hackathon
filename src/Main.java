import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        
        menuRoot.setCenter(bttn);
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
        
    }
}
