

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    
    private ImageView iv;
    private final int count;
    private final int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;
    
    private int lastIndex;
    
    public SpriteAnimation(String image, int count, int columns, int offsetX, int offsetY,
                           int width, int height, Duration duration) {
        this.iv = new ImageView(image);
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            iv.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
    
    public void setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }
    
    public ImageView getIV() {
        return iv;
    }
    
    public void setIV(String image){
        this.iv = new ImageView(image);
        this.iv.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }
}
