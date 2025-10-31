import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

import java.net.URL;

public class Bonus extends Brick{

    private int yVelocity = 5;

    private int type;

    public Bonus(Rectangle rect, int type) {
        super(rect);
        /*Random rand = new Random();
        this.type = rand.nextInt(3) + 1;
        System.out.println(type);*/
        this.type = type;

        URL imagePath = Bonus.class.getResource("/bonus" + type+ ".png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());
    }

    public int getType() {
        return this.type;
    }

    public void move() {
        this.setY(this.getY() + yVelocity);
    }

    public void move(double x, double y) {
        if (this.type == 4
                || this.type == 6
                || this.type == 9
                || this.type == 11) {
            this.move();
        } else {
            PVector speed = new PVector(x - this.getX(), y - this.getY());
            speed.normalize();
            speed.mult(3);

            this.setX(this.getX() + speed.getX());
            this.setY(this.getY() + speed.getY());
        }
    }

    public boolean detectCollision(Platform platform) {
        if (platform.getHitBox().intersects(this.getHitBox().getLayoutBounds())){
            return true;
        }
        return false;
    }

}
