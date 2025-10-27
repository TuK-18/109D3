import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;


public class Ball extends GameObject {
    public int fieldWidth = 550;
    public int fieldHeight = 640;
    private double radius ;
    private double centreX;
    private double centreY;

    public Ball(Circle shape) {
        this.hitBox2 = shape;

        this.radius = shape.getRadius();
        this.centreX = shape.getCenterX();
        this.centreY = shape.getCenterY();
        double aa = -3.055052212747844;
        double aaa = -3.6132047217868;
        this.vSpeed = new PVector(aa, aaa);
        //this.vSpeed = new PVector(0,0);

    }

    public double getRadius() {
        return this.radius;
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
