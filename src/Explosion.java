
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Explosion extends Pane {

    ImageView iv;
    int width;
    int height;
    double x; //Explosion xPos
    double y; //Explosion yPos
    
    SpriteAnimation explosionnn;
    private final int count = 4;
    private final int columns = 4;
    private final int offsetX = 0;
    private final int offsetY = 0;
    private final Duration duration = Duration.millis(900);
    private final Animation animation;
    
    boolean kill = false;
        
    public Explosion(String img, int posX, int posY, int width, int height) {
        Image projImage = new Image(img);
        ImageView exploIV = new ImageView(projImage);
        this.iv = exploIV;
        explosionnn = new SpriteAnimation(img, count, columns, offsetX, offsetY, width, height, duration); 
        this.iv = explosionnn.getIV();
        animation = explosionnn;
        animation.play();
        this.getChildren().addAll(iv);
        this.setTranslateX(posX);
        this.setTranslateY(posY);
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;
    }
    
    /*long spawnTime = 0;
    public void move() {
        long timeNow = System.currentTimeMillis();
	long time = timeNow - spawnTime;
        if (time < 0 || time > 1000) {
            spawnTime = timeNow;
            kill = true;
        }
    }*/
    
    public boolean getKill() {
        return kill;
    }
}