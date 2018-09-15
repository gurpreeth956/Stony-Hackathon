import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;
    Scene scene;
    static Pane gameArea;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        gameArea = new Pane();
        scene = new Scene(gameArea, 200, 200);
        
        Button bttn = new Button("Start"); //Button to start game
        bttn.setOnAction(e -> {
            stage.close();
        });
        
        gameArea.getChildren().add(bttn);
        stage.setTitle("The Elimination of Space Pollution");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

}