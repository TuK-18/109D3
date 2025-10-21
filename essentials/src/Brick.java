import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Brick extends GameObject{
    private int density;
    public boolean breakable;

    private Image brokenImage;

    public Brick(Rectangle rect) {
        super(rect);

        URL imagePath = Brick.class.getResource("/brick1.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        URL brokenImagePath = Brick.class.getResource("/brokenBrick1.png");
        assert brokenImagePath != null;
        this.brokenImage = new Image(brokenImagePath.toExternalForm());

        this.hitBox1 = rect;
        this.density = 2;
        this.breakable = true;
        //this.breakable = false;
    }

    public Brick(Rectangle rect, int density) {
        super(rect);

        URL imagePath = Brick.class.getResource("/brick" + density + ".png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        URL brokenImagePath = Brick.class.getResource("/brokenBrick" + density + ".png");
        assert brokenImagePath != null;
        this.brokenImage = new Image(brokenImagePath.toExternalForm());

        this.hitBox1 = rect;
        this.density = density;
        this.breakable = true;
    }

    public Brick(Rectangle rect, boolean breakable) {
        super(rect);

        URL imagePath = Brick.class.getResource("/brick5.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        this.hitBox1 = rect;
        this.breakable = breakable;
    }

    public void reduceDensity() {
        if (this.density > 0 && this.breakable) {
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

    public void render(GraphicsContext gc) {
        if(this.density == 1) {
            gc.drawImage(this.brokenImage, this.getX(), this.getY());
        } else {
            gc.drawImage(this.image, this.getX(), this.getY());
        }
    }

}

