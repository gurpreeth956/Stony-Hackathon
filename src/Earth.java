
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Earth extends Pane {

	public static ImageView iv;
	int offsetX = 0;
	int offsetY = 0;
	int width;
	int height;
	public int x; //Earth xPos
	public int y; //Earth yPoS
	int screenWidth;
	int screenHeight;

	public boolean alive = true;
	public int health;
	public int totalHealth;
        
        public List<Rectangle> collisionRects;
        public Rectangle middle, left, right;

	public Earth(String img, int health, int width, int height, int screenWidth, int screenHeight) {
		Image earthImage = new Image(img);
		ImageView earthIV = new ImageView(earthImage);
		this.iv = earthIV;
		this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		this.health = health;
		this.totalHealth = health;
                this.getChildren().addAll(iv);

		this.width = width;
		this.height = height;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		setPosition();
                
                collisionRects = new ArrayList();
                middle = new Rectangle(this.getTranslateX() + 37, this.getTranslateY() + 10, 87, 140);
                middle.setFill(Color.TRANSPARENT);
                left = new Rectangle(this.getTranslateX() + 9, this.getTranslateY() + 37, 30, 86);
                left.setFill(Color.TRANSPARENT);
                right = new Rectangle(this.getTranslateX() + 121, this.getTranslateY() + 37, 30, 86);
                right.setFill(Color.TRANSPARENT);
                collisionRects.add(middle);
                collisionRects.add(left);
                collisionRects.add(right);
	}

	public void setPosition() {
		this.setTranslateX((screenWidth / 2) - (width / 2));
		this.setTranslateY((screenHeight / 2) - (height / 2));
		this.x = (screenWidth / 2);
		this.y = (screenHeight / 2);
	}

	public void hit() {
		health--;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
        
        public int getHealth() {
            return health;
        }
}
