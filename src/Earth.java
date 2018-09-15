
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

	public static Rectangle healthBarOutline;
	public static Rectangle actualHealth;
	public static Rectangle lostHealth;
        public static Label nameLabel;
	public boolean alive = true;
	public int health;
	public int totalHealth;

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
                
                nameLabel = new Label("EARTH");
                nameLabel.setFont(new Font("Arial", 25));
                nameLabel.setTextFill(Color.WHITE);
                nameLabel.setTranslateX(10);
                nameLabel.setTranslateY(700);
                healthBarOutline = new Rectangle(355, 20, 541, 20);
                healthBarOutline.setFill(Color.TRANSPARENT);
                healthBarOutline.setStroke(Color.BLACK);
                lostHealth = new Rectangle(356, 21, 540, 19);
                lostHealth.setFill(Color.RED);
                actualHealth = new Rectangle(356, 21, 540, 19);
                actualHealth.setFill(Color.GREEN);
                actualHealth.toFront();
	}

	public void setPosition() {
		this.setTranslateX((screenWidth / 2) - (width / 2));
		this.setTranslateY((screenHeight / 2) - (height / 2));
		this.x = (screenWidth / 2);
		this.y = (screenHeight / 2);
	}

	public void hit() {
		health--;
                actualHealth = new Rectangle(356, 21, health * (540 / this.totalHealth), 19);
                actualHealth.setFill(Color.GREEN);
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
