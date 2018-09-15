
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

	//Create damage variable for player hit?
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

        

	public Debris(String img, double x, double y, int health, int coin, int width, int height, Rectangle2D screenSize) {
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.x = x;
		this.y = y;
		//calcDistances(screenSize);
		linepoints(screenSize, 350);
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

	public void move(Rectangle2D screenSize) {
		if (pointer < xpoints.length) {
			this.setTranslateX(xpoints[pointer]);
			this.setTranslateY(ypoints[pointer]);
                        this.setX(xpoints[pointer]);
                        this.setY(ypoints[pointer]);
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
		return this.getBoundsInParent().intersects(player.getBoundsInParent());
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
