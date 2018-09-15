
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Debris extends Pane {

    //Create damage variable for player hit??
    public ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    public int x; //Enemy xPos
    public int y; //Enemy yPos
    int coin;
    int score;
    int enemySpeed;

    public Rectangle healthBarOutline;
    public Rectangle actualHealth;
    public Rectangle lostHealth;
    public boolean alive = true;
    public int health;
    public int totalHealth;

    //currently used only for bosses
    public Label nameLabel;

    public Debris(String img, int health, int coin, int width, int height) {
            Image enemyImage = new Image(img);
            ImageView enemyIV = new ImageView(enemyImage);
            this.iv = enemyIV;
            this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
            this.health = health;
            this.totalHealth = health;
            this.coin = coin;
            this.score = coin;
            this.width = width;
            this.height = height;
            this.getChildren().addAll(iv);

            healthBarOutline = new Rectangle(x - 1, y - 6, width + 2, 4);
            healthBarOutline.setFill(Color.TRANSPARENT);
            healthBarOutline.setStroke(Color.BLACK);
            lostHealth = new Rectangle(x, y - 5, width, 3);
            lostHealth.setFill(Color.RED);
            actualHealth = new Rectangle(x, y - 5, width, 3);
            actualHealth.setFill(Color.GREEN);
            actualHealth.toFront();
    }

    public void setCharacterView(int offsetX, int offsetY) {
            this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void move(Character player, double width, double height) { //note width and height here are screen size
            //Create equation to make 

    }

}
