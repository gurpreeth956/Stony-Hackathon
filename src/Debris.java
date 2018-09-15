
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

    //Create damage variable for player hit??
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

    public Rectangle healthBarOutline;
    public Rectangle actualHealth;
    public Rectangle lostHealth;
    public boolean alive = true;
    public int health;
    public int totalHealth;

    //currently used only for bosses
    public Label nameLabel;

    public Debris(String img, double x, double y,int health, int coin, int width, int height, Rectangle2D screenSize) {
            this.setTranslateX(x);
            this.setTranslateY(y);
            this.x = x;
            this.y = y;
            calcDistances(screenSize);
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

   public void move(Rectangle2D screenSize) { //note width and height here are screen size
		//Create equation to make 
            double centerx = screenSize.getWidth()/2;
            double centery = screenSize.getHeight()/2;
            if(x + 80 <= centerx && y + 80 <= centery){ //quad 2
                moveX(xdist/ydist, ydist/xdist);
                moveY(ydist/xdist, xdist/ydist);
            }
            if(x + 80 > centerx && y + 80 <= centery){ //quad 1
                moveX(xdist/ydist, -1*(ydist/xdist));
                moveY(ydist/xdist, xdist/ydist);
            }
            if(x + 80 <= centerx && y + 80 > centery){ //quad 3
                moveX(xdist/ydist, ydist/xdist);
                moveY(ydist/xdist, -1*(xdist/ydist));
            }
            if(x + 80 > centerx && y + 80 > centery){ //quad 4
                moveX(xdist/ydist, -1*(ydist/xdist));
                moveY(ydist/xdist, -1*(xdist/ydist));
            }
	}
   
           public void moveX(double x, double xspeed) { //x is horizontal speed
            for(double i = 0; i < x; i++){
                this.setTranslateX(this.getTranslateX()+xspeed);
                this.x += xspeed;
            }
        }
    
        public void moveY(double y,double yspeed) { //y is vertical speed
            for(double i = 0; i < y; i++){
                this.setTranslateY(this.getTranslateY()+yspeed);
                this.y += yspeed;
            }
        }
        
        public void calcDistances(Rectangle2D screenSize){
            double centerx = screenSize.getWidth()/2;
            double centery = screenSize.getHeight()/2;
            if(x + 80 <= centerx)
                xdist = centerx - x + 80;
            else
                xdist = x + 80 - centerx;
            if(y + 80 <= centery)
                ydist = centery - y + 80;
            else
                ydist = y + 80 - centery;
        }

	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public boolean isColliding(Player player){
            return this.getBoundsInParent().intersects(player.getBoundsInParent());
	}
        
        public boolean isEarthColliding(Earth earth){
           return this.getBoundsInParent().intersects(earth.getBoundsInParent());

        }

}
