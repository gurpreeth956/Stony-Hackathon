
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {
    
    ImageView iv;
    int offsetX = 0;
    int offsetY = 0;
    int width = 0;
    int height = 0;
    
    int x; //Character xPos
    int y; //Character yPos
    
    int health = 5;
    boolean alive = true;
    
    public Player(int posX, int posY) {
        this.x = posX;
        this.y = posY;
    }
    
    public void moveClockwise(boolean dir) {
        
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
    
    
}