
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Projectile extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width;
    int height;
    int x; //Proj xPos
    int y; //Proj yPos

	double vx;
	double vy;
	double targetX;
	double targetY;
	double rotation;
	boolean speedSet = false;

	Point2D velocity;
	int velocityX = 0;
	int velocityY = 0;
	boolean alive = true;

    public Projectile(String img, int posX, int posY, int width, int height) {
            Image projImage = new Image(img);
            ImageView projIV = new ImageView(projImage);
            this.iv = projIV;
            this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
            this.setTranslateX(posX);
            this.setTranslateY(posY);
            this.x = posX;
            this.y = posY;
            this.width = width;
            this.height = height;
            this.getChildren().addAll(iv);
    }

	/*public void move() {
		this.setTranslateX(this.getTranslateX() + this.getVelocityX());
		this.setTranslateY(this.getTranslateY() + this.getVelocityY());
	}*/
	public void move(Earth earth) {
		targetX = earth.getX() - this.x;
		targetY = earth.getY() - this.y;
		rotation = Math.atan2(targetY, targetX) * 180 / Math.PI;
		this.iv.setRotate(rotation);

		if (!speedSet) {
			vx = -1*(this.getVelocityX() * (90 - Math.abs(rotation)) / 90);
			if (rotation < 0) {
				vy = -1*(-this.getVelocityX() + Math.abs(vx));
				speedSet = true;
			} else {
				vy = -1*(this.getVelocityX() - Math.abs(vx));
				speedSet = true;
			}
		}

		this.setTranslateX(this.getTranslateX() + (int) vx);
		this.setTranslateY(this.getTranslateY() + (int) vy);
		this.x += (int) vx; //X is int and vx is double 
		this.y += (int) vy; //casting to int improves missile accuracy
	}

	public int getVelocityX() {
		return velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean enemyColliding(Debris debris) {
		return this.getBoundsInParent().intersects(debris.getBoundsInParent());
	}
}
