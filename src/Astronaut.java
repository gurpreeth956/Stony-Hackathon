
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Astronaut extends Pane {
	public ImageView iv;
	int offsetX = 0;
	int offsetY = 0;
	int width;
	int height;
	public double x; //Enemy xPos
	public double y; //Enemy yPos
	int coin;
	int score;
	int enemySpeed;

	public double xdist;
	public double ydist;
	public double[] xpoints;
	public double[] ypoints;
	public int pointer = 0;

	public Rectangle healthBarOutline;
	public Rectangle actualHealth;
	public Rectangle lostHealth;
	public boolean alive = true;
	public int health;
	public int totalHealth;
        
        public List<Rectangle> collisionRects;
        public Rectangle middle;

	public Astronaut(String img, double x, double y, int health, int coin, int width, int height, Rectangle2D screenSize){
			this.setTranslateX(x);
		this.setTranslateY(y);
		this.x = x;
		this.y = y;
		//calcDistances(screenSize);
		linepoints(screenSize, 250);
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

		collisionRects = new ArrayList();
                middle = new Rectangle(this.getTranslateX() + 20, this.getTranslateY() + 12, 10, 28);
                middle.setFill(Color.TRANSPARENT);
                collisionRects.add(middle);
	}

	public void setCharacterView(int offsetX, int offsetY) {
		this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
	}
        
        public void updateHit() {
            middle.setX(this.getTranslateX());
            middle.setY(this.getTranslateY());
        }

	public void move(Rectangle2D screenSize) {
		if (pointer < xpoints.length) {
			this.setTranslateX(xpoints[pointer]);
			this.setTranslateY(ypoints[pointer]);
			pointer++;
		}

	}

	public void linepoints(Rectangle2D screenSize, int speed) {
		xpoints = new double[speed];
		ypoints = new double[speed];
		double centerx = screenSize.getWidth() / 2;
		double centery = screenSize.getHeight() / 2;
		double xinc;
		double yinc;
		if (x + 80 <= centerx) {
			xinc = (centerx - x + 80) / speed;
		} else {
			xinc = (x + 80 - centerx) / speed;
		}
		if (y + 80 <= centery) {
			yinc = (centery - y + 80) / speed;
		} else {
			yinc = (y + 80 - centery) / speed;
		}
		double tempx = x;
		double tempy = y;
		for (int i = 0; i < speed; i++) {
			xpoints[i] = tempx;
			ypoints[i] = tempy;
			if (x + 80 <= centerx && y + 80 <= centery) { //quad 2
				tempx += xinc;
				tempy += yinc;
			}
			if (x + 80 > centerx && y + 80 <= centery) { //quad 1
				tempx -= xinc;
				tempy += yinc;
			}
			if (x + 80 <= centerx && y + 80 > centery) { //quad 3
				tempx += xinc;
				tempy -= yinc;
			}
			if (x + 80 > centerx && y + 80 > centery) { //quad 4
				tempx -= xinc;
				tempy -= yinc;
			}
		}
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isColliding(Player player) {
            boolean colliding = false;
            for (Rectangle rect : this.collisionRects) {
                if (this.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    colliding = true;
                }
            }
            return colliding;
	}

	public boolean isEarthColliding(Earth earth) {
            boolean colliding = false;
            for (Rectangle rect : earth.collisionRects) {
		if (this.getBoundsInParent().intersects(rect.getBoundsInParent())) {
                    colliding = true;
                }
            }
            return colliding;
	
	}
}
