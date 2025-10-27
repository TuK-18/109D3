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

    public boolean detectCollision(Platform platform) {
        if (platform.getHitBox().intersects(this.getHitBox().getLayoutBounds())){
            return true;
        }
        return false;
    }

}