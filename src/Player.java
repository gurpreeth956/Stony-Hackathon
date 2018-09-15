
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {

    ImageView iv;
    int offsetX = 9;
    int offsetY = 7;
    int width = 0;
    int height = 0;
    int screenWidth;
    int screenHeight;
	int score = 0;
    Point2D velocity;

    int x; //Character xPos
    int y; //Character yPos

    int health = 5;
    boolean alive = true;

    public Player(String img, int health, int width, int height, int screenWidth, int screenHeight) {
        Image enemyImage = new Image(img);
        ImageView enemyIV = new ImageView(enemyImage);
        this.iv = enemyIV;
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        this.width = width;
        this.height = height;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.getChildren().addAll(iv);
        setPosition();
    }
    
    public void setPosition(){
        this.setTranslateX((screenWidth/2)-(width/2));
        this.setTranslateY((screenHeight/2)-(height/2) + 100);
	this.x = (screenWidth/2)-(width/2);
        this.y = (screenHeight/2)-(height/2) + 100;
    }

    long timeOfLastMove = 0;
    
    public void moveClockwise(boolean dir, int angleChange) {
        long timeNow = System.currentTimeMillis();
        long time = timeNow - timeOfLastMove;
        
        if (time < 0 || time > 25) {
            if (dir) {
                this.setTranslateX(((screenWidth/2)-(width/2)) + 100 * (Math.cos(this.getRotate() - angleChange)));
                this.setTranslateY(((screenHeight/2)-(height/2)) + 100 * (Math.sin(this.getRotate() - angleChange)));
                this.setX(((screenWidth/2)-(width/2)) + (int)(100 * Math.cos(this.getRotate() - angleChange)));
                this.setY(((screenHeight/2)-(height/2)) + (int)(100 * Math.sin(this.getRotate() - angleChange)));
                this.setRotate(this.getRotate() - angleChange);
                //this.setRotate(Math.atan((this.getY() - ((screenHeight/2)))/(this.getX() - ((screenWidth/2)))));
                //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
            } else {
                this.setTranslateX(((screenWidth/2)-(width/2)) + 100 * (Math.cos(this.getRotate() + angleChange)));
                this.setTranslateY(((screenHeight/2)-(height/2)) + 100 * (Math.sin(this.getRotate() + angleChange)));
                this.setX(((screenWidth/2)-(width/2)) + (int)(100 * Math.cos(this.getRotate() + angleChange)));
                this.setY(((screenHeight/2)-(height/2)) + (int)(100 * Math.sin(this.getRotate() + angleChange)));
                this.setRotate(this.getRotate() + angleChange);
            }
            timeOfLastMove = timeNow;
        }
    }

	public void hit(){
		health--;
	}
	
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
	
	public Point2D getVelocity(){
		return velocity;
	}
	
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
	
	public int getHealth(){
		return health;
	}
	
	public void increaseScore(){
		score++;
	}
	
	public void decreaseScore(){
		score--;
	}
	
	public int getScore(){
		return score;
	}
        
        public void setScore(int i) {
            this.score = i;
        }

}
