
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

	public Rectangle healthBarOutline;
	public Rectangle actualHealth;
	public Rectangle lostHealth;
	public boolean alive = true;
	public int health;
	public int totalHealth;

	public Earth(String img, int health, int width, int height) {
		Image enemyImage = new Image(img);
		ImageView enemyIV = new ImageView(enemyImage);
		this.iv = enemyIV;
		this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		this.health = health;
		this.totalHealth = health;

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
}
