
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

    public void move() {
        this.setTranslateX(this.getTranslateX() + this.getVelocityX());
        this.setTranslateY(this.getTranslateY() + this.getVelocityY());
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
