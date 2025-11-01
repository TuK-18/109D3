import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.net.URL;

public class Bullet extends Ball{

    public Bullet(Circle shape) {
        super(shape);
        this.vSpeed = new PVector(0,-5);
    }

    @Override
    public void move() {
        PVector newSpeed = this.vSpeed.get();
        newSpeed.addX(this.getCentreX());
        newSpeed.addY(this.getCentreY());
        this.setCentreX(newSpeed.getX());
        this.setCentreY(newSpeed.getY());
    }


    public boolean detectCollision(GameObject o) {

        if (o != this && o.getHitBox().intersects(this.getHitBox().getLayoutBounds())) {
            return true;
        }

        return false;
    }
}