package Arkanoid.GameObjects;

import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Brick extends GameObject {
    protected int type;
    private int density;
    public boolean breakable;

    private transient Image brokenImage;

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

        this.type = density;
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
        this.density = 5;
    }

    public void reduceDensity(int x) {
        if (this.density > 0 && this.breakable) {
            this.density -= x;
        }
    }

    public int getType(){
        return this.type;
    }

    public Shape getHitBox() {
        return this.hitBox1;
    }

    public int getDensity() {
        return this.density;
    }

    /*public void handleHit(Arkanoid.GameObjects.Ball b) {
        if(this.getHitBox().intersects(b.getHitBox().getLayoutBounds())){
            reduceDensity();
        }
    }*/

    public boolean isBreakable() {
        return this.breakable;
    }

    public void render(GraphicsContext gc) {
        if(this.density <= this.type/2 && breakable) {
            gc.drawImage(this.brokenImage, this.getX(), this.getY());
        } else {
            gc.drawImage(this.image, this.getX(), this.getY());
        }
    }

    public void loadImage() {
        URL imagePath = Brick.class.getResource("/brick" + this.type + ".png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        loadBrokenImage();
    }

    public void loadBrokenImage() {
        if (type == 5) return;

        URL brokenImagePath = Brick.class.getResource("/brokenBrick" + type + ".png");
        assert brokenImagePath != null;
        this.brokenImage = new Image(brokenImagePath.toExternalForm());

    }

    @Override
    public boolean handleObjectCollision(GameObject otherObject) {
        // khong lam gi ca
        return false;
    }
}

