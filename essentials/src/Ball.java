import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.net.URL;


public class Ball extends GameObject {
    public int fieldWidth = 550;
    public int fieldHeight = 640;
    private double radius ;
    private double centreX;
    private double centreY;

    public Ball(Circle shape) {
        this.hitBox2 = shape;

        /*URL imagePath = Ball.class.getResource("/ball.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());*/

        this.radius = shape.getRadius();
        this.centreX = shape.getCenterX();
        this.centreY = shape.getCenterY();
        /*double aa = -2.055052212747844;
        double aaa = -2.6132047217868;*/
        double aa = -3.055052212747844;
        double aaa = -3.6132047217868;
        this.vSpeed = new PVector(aa, aaa);
        //this.vSpeed = new PVector(0,0);

    }

    public double getRadius() {
        return this.radius;
    }

    public double getX() {
        return this.getCentreX() - 10;
    }

    public double getY() {
        return this.getCentreY() - 10;
    }

    public void render(GraphicsContext gc) {

        gc.drawImage(this.image,this.getX() - this.getRadius()
                , this.getY() - this.getRadius());
    }


    @Override
    public void move() {
        /*System.out.print(this.getCentreX());
        System.out.print(" ");
        System.out.println(this.getCentreY());*/

        PVector newSpeed = this.vSpeed.get();
        newSpeed.addX(this.getCentreX());
        newSpeed.addY(this.getCentreY());
        this.setCentreX(newSpeed.getX());
        this.setCentreY(newSpeed.getY());

        if(this.getCentreX() <= this.radius) {
            this.setCentreX(radius);
            this.vSpeed.multX(-1);
        }

        if(this.getCentreX() >= this.fieldWidth - this.radius) {
            this.setCentreX(this.fieldWidth - this.radius);
            this.vSpeed.multX(-1);
        }

        if(this.getCentreY() <= this.radius) {
            this.setCentreY(radius);
            this.vSpeed.multY(-1);
        }

        /*if(this.getCentreY() >= this.fieldHeight - this.radius) {
            this.setCentreY(this.fieldHeight - this.radius);
            this.vSpeed.multY(-1);
        }*/
    }

}