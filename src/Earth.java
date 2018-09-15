
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Earth extends Pane {

	public ImageView iv;
	int offsetX = 0;
	int offsetY = 0;
	int width;
	int height;
	public int x; //Earth xPos
	public int y; //Earth yPOS
	int screenWidth;
	int screenHeight;

	public Rectangle healthBarOutline;
	public Rectangle actualHealth;
	public Rectangle lostHealth;
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

		this.width = width;
		this.height = height;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.getChildren().addAll(iv);
		setPosition();

		healthBarOutline = new Rectangle(x - 1, y - 6, width + 2, 4);
		healthBarOutline.setFill(Color.TRANSPARENT);
		healthBarOutline.setStroke(Color.BLACK);
		lostHealth = new Rectangle(x, y - 5, width, 3);
		lostHealth.setFill(Color.RED);
		actualHealth = new Rectangle(x, y - 5, width, 3);
		actualHealth.setFill(Color.GREEN);
		actualHealth.toFront();
	}
	
	public void setPosition(){
		this.setTranslateX((screenWidth/2)-(width/2));
		this.setTranslateY((screenHeight/2)-(height/2));
		this.x = (screenWidth/2)-(width/2);
        this.y = (screenHeight/2)-(height/2);
	}
}
