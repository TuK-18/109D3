import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameObject {
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
        if(this instanceof Platform){
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

    public boolean detectCollision(GameObject o) {
        if (o != this && o.getHitBox().intersects(this.getHitBox().getLayoutBounds())
                && this instanceof Ball
                && (o instanceof Platform || o instanceof Brick)) {
            /*PVector currSpeed = this.vSpeed.get();
            this.setSpeed(o.vSpeed);
            o.setSpeed(currSpeed);*/
            //System.out.println("ball hits pl");
            //this.vSpeed.multY(-1);
            //this.vSpeed.multX(-1);
            //this.vSpeed.multX(-1);

            /*if(this.getCentreY() + this.radius >= o.getY()
                    && (this.getCentreX() + this.radius >= o.getX()
                    || this.getCentreX() - this.radius <= o.getX()+o.getW()) ){
                //System.out.println(o.getX());
                this.vSpeed.multX(-1);
                return;
            }*/

            /*if(collisionManager.hitBottoms(o, this)){
                this.setCentreY(this.getCentreY() - this.getRadius());
                this.vSpeed.multY(-1);
                //return;
            } if(collisionManager.hitSides(o,this)) {
                this.setCentreX(this.getCentreX() - this.getRadius());
                this.vSpeed.multY(-1);
                this.vSpeed.multX(-1);
                //return;dd
            }*/

            if (collisionManager.hitTop(o,this)) {

                if(o instanceof Platform) {
                    
                    this.setCentreY(this.getCentreY() - this.getRadius());
                    this.vSpeed.multY(-1);
                    //System.out.println(((Platform) o).getxVelocity());


                    //this one only modifies ball speed
                    /*if (Math.abs(this.vSpeed.getX()) < 5) {
                        this.vSpeed.setX(this.vSpeed.getX() + 0.1
                                * ((double) ((Platform) o).getxVelocity() / 2));
                    }*/

                    //this one modifies the ball's direction and speed
                    if ((this.vSpeed.getX() > 0 && ((Platform) o).getxVelocity() < 0)
                        || (this.vSpeed.getX() < 0 && ((Platform) o).getxVelocity() > 0)) {
                        //this.vSpeed.setX(this.vSpeed.getX() * -1)
                        this.vSpeed.multX(-1);
                    } else {
                        if (Math.abs(this.vSpeed.getX()) < 5) {
                            this.vSpeed.setX(this.vSpeed.getX() + 0.1
                                    * ((double) ((Platform) o).getxVelocity() / 2));
                        }
                    }
                    return true;
                }
                SoundManager.playClip1();
                this.setCentreY(this.getCentreY() - this.getRadius());
                //this.setCentreY(this.getCentreY());
                this.vSpeed.multY(-1);
                return true;
            }

            if(collisionManager.hitLeft(o,this)) {
                this.setCentreX(this.getCentreX() - this.getRadius());
                //this.vSpeed.multY(-1);
                this.vSpeed.multX(-1);
                return true;
            }

            if(collisionManager.hitRight(o,this)) {
                this.setCentreX(this.getCentreX() + this.getRadius());
                //this.vSpeed.multY(-1);
                this.vSpeed.multX(-1);
                return true;
            }

            if (collisionManager.hitBottom(o,this)) {
                this.setCentreY(this.getCentreY() + this.getRadius());
                this.vSpeed.multY(-1);
                return true;
            }


            //this.vSpeed.multY(-1);


        }
        return false;
    }

    public PVector getvSpeed() {
        return this.vSpeed;
    }

}
