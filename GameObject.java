
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public abstract class GameObject {
    protected PVector vSpeed;
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected CollisionManager collisionManager;

    protected Rectangle hitBox1;
    protected Circle hitBox2;
    protected Color color;

    protected Image image;

    public int fieldWidth = 600;
    public int fieldHeight = 640;
    protected double radius ;
    protected double centreX;
    protected double centreY;

    public GameObject(){
        collisionManager = new CollisionManager();
    }
    public GameObject(Rectangle rect) {
        collisionManager = new CollisionManager();
        this.x = rect.getX();
        this.y = rect.getY();
        this.w = rect.getWidth();
        this.h = rect.getHeight();
        this.hitBox1 = rect;
        //hitBox.setX(x_);

        //this.color = color_;
    }

    public double getX() {
        return this.hitBox1.getX();
    }

    public void setX(double x) {
        this.hitBox1.setX(x);
        this.x = x;

    }

    public double getY() {
        return this.hitBox1.getY();
    }

    public void setY(double y) {
        this.hitBox1.setY(y);
        this.y = y;

    }

    public double getW() {
        return this.hitBox1.getWidth();
    }

    public void setW(double w) {
        this.w = w;
        this.hitBox1.setWidth(w);
    }

    public double getH() {
        return this.hitBox1.getHeight();
    }

    public void setH(double h) {
        this.h = h;
        this.hitBox1.setHeight(h);
    }

    public Shape getHitBox() {
        if(this instanceof Platform || this instanceof Brick){
            return hitBox1;
        }
        return hitBox2;
    }

    /*public void setHitBox(Shape hitBox) {
        this.hitBox = hitBox;
    }*/

    public void setSpeed(PVector speed_) {
        this.vSpeed = speed_;
    }

    public double getCentreX() {
        return this.hitBox2.getCenterX();
    }

    public void setCentreX(double centreX) {
        this.hitBox2.setCenterX(centreX);
        this.centreX = centreX;
    }

    public double getCentreY() {
        return this.hitBox2.getCenterY();
    }

    public void setCentreY(double centreY) {
        this.hitBox2.setCenterY(centreY);
        this.centreY = centreY;

    }

    public double getRadius() {
        return this.hitBox2.getRadius();
    }

    public double getInitialSpeed() {
        return (Math.random() * 5 + 1);
    }

    /*public void setSpeed(PVector speed_) {
        this.vSpeed = speed_;
    }*/

    public void move() {

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image,this.getX(), this.getY());
    }


    public PVector getvSpeed() {
        return this.vSpeed;
    }





    public abstract boolean handleObjectCollision(GameObject o);
}