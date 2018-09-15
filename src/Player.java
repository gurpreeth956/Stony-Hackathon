
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {

    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 0;
    int height = 0;
    int screenWidth;
    int screenHeight;
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

    public void moveClockwise(boolean dir) {
        if (dir) {
            this.setTranslateX(((screenWidth/2)-(width/2)) + 100 * (int)(Math.cos((int)this.getRotate() - 5)));
            this.setTranslateY(((screenHeight/2)-(height/2)) + 100 * (int)(Math.sin((int)this.getRotate() - 5)));
            //System.out.println(("cos" + (100 * Math.cos((int)this.getRotate() - 5))));
            //System.out.println(("sin" + (100 * Math.sin((int)this.getRotate() - 5))));
            this.setX(((screenWidth/2)-(width/2)) + 100 * (int)Math.cos((int)this.getRotate() - 5));
            this.setY(((screenHeight/2)-(height/2)) + 100 * (int)Math.sin((int)this.getRotate() - 5));
            //this.setRotate(this.getRotate() - 5);
            //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        } else {
            //iv.setRotate(iv.getRotate() - 5);
            //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        }
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

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

}
