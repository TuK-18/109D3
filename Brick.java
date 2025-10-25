import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class Brick extends GameObject{
    private int density;
    public boolean breakable;
    public Brick(Rectangle rect) {

        super(rect);
        this.hitBox1 = rect;
        this.density = 2;
        this.breakable = true;
        //this.breakable = false;
    }

    public Brick(Rectangle rect, int density) {
        super(rect);
        this.hitBox1 = rect;
        this.density = density;
        this.breakable = true;
    }

    public Brick(Rectangle rect, boolean breakable) {
        super(rect);
        this.hitBox1 = rect;
        this.breakable = breakable;
    }

    public void reduceDensity() {
        if (this.density > 0) {
            this.density -= 1;
        }
    }

    public Shape getHitBox() {
        return this.hitBox1;
    }

    public int getDensity() {
        return this.density;
    }

    /*public void handleHit(Ball b) {
        if(this.getHitBox().intersects(b.getHitBox().getLayoutBounds())){
            reduceDensity();
        }
    }*/

    public boolean isBreakable() {
        return this.breakable;
    }

}

