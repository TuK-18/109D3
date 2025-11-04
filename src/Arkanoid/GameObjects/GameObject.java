package Arkanoid.GameObjects;

import Arkanoid.CollisionManager;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;


public abstract class GameObject implements Serializable {
    protected PVector vSpeed;
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    //protected CollisionManager collisionManager;

    protected transient Rectangle hitBox1;
    protected transient Circle hitBox2;


    protected transient Image image;

    protected double centreX;
    protected double centreY;

    public GameObject(){
        //collisionManager = new CollisionManager();
    }
    public GameObject(Rectangle rect) {
        //collisionManager = new CollisionManager();
        this.x = rect.getX();
        this.y = rect.getY();
        this.w = rect.getWidth();
        this.h = rect.getHeight();
        this.hitBox1 = rect;
        //hitBox.setX(x_);
        //this.color = color_;
    }

    public double getX() {
        //return this.hitBox1.getX();
        return this.x;
    }

    public void setX(double x) {
        this.hitBox1.setX(x);
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.hitBox1.setY(y);
        this.y = y;
    }

    public double getW() {
        //return this.hitBox1.getWidth();
        return this.w;
    }

    public void setW(double w) {
        this.w = w;
        this.hitBox1.setWidth(w);
    }

    public double getH() {
        return this.h;
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

    public void setHitBox(Rectangle hitBox) {
        hitBox1 = hitBox;
    }

    public void setHitBox(Circle hitBox) {
        hitBox2 = hitBox;
    }

    public void setSpeed(PVector speed_) {
        this.vSpeed = speed_;
    }

    public double getCentreX() {
        return this.centreX;
    }

    public void setCentreX(double centreX) {
        this.hitBox2.setCenterX(centreX);
        this.centreX = centreX;
    }

    public double getCentreY() {
        return this.centreY;
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

    /*public void setSpeed(Arkanoid.GameObjects.PVector speed_) {
        this.vSpeed = speed_;
    }*/

    public void move() {

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image,this.getX(), this.getY());
    }

    public void loadImage() {

    }

    public PVector getvSpeed() {
        return this.vSpeed;
    }


    public abstract boolean handleObjectCollision(GameObject o);
}