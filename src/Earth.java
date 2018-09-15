
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Earth extends Pane {

	public ImageView iv;
	int offsetX = 0;
	int offsetY = 0;
	int width;
	int height;
	public int x; //Enemy xPos
	public int y; //Enemy yPos

	public Rectangle healthBarOutline;
	public Rectangle actualHealth;
	public Rectangle lostHealth;
	public boolean alive = true;
	public int health;
	public int totalHealth;

}
