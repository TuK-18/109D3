package Arkanoid.GameObjects;

import Arkanoid.SoundManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.*;


public class Ball extends GameObject {
    public int fieldWidth = 550;
    public int fieldHeight = 640;
    private double radius ;
    private double centreX;
    private double centreY;

    public Ball(Circle shape) {
        this.hitBox2 = shape;

        /*URL imagePath = Arkanoid.GameObjects.Ball.class.getResource("/ball.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());*/

        this.radius = shape.getRadius();
        this.centreX = shape.getCenterX();
        this.centreY = shape.getCenterY();
        /*double aa = -2.055052212747844;
        double aaa = -2.6132047217868;*/
        double aa = -4.055052212747844;
        double aaa = -4.6132047217868;
        this.vSpeed = new PVector(aa, aaa);
        //this.vSpeed = new Arkanoid.GameObjects.PVector(0,0);
    }

    public PVector getSpeed() {
        return this.vSpeed;
    }

    public double getScalarSpeed() {
        double vx = this.vSpeed.getX();
        double vy = this.vSpeed.getY();
        double speed = Math.sqrt(vx * vx +vy * vy);
        return speed;
    }

    public double getRadius() {
        //return this.radius;
        return this.hitBox2.getRadius();
    }

    public double getX() {
        return this.getCentreX() - this.getRadius();
    }

    public double getY() {
        return this.getCentreY() - this.getRadius();
    }

    @Override
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
            SoundManager.playClip2();
            this.setCentreX(radius);
            this.vSpeed.multX(-1);
        }

        if(this.getCentreX() >= this.fieldWidth - this.radius) {
            SoundManager.playClip2();
            this.setCentreX(this.fieldWidth - this.radius);
            this.vSpeed.multX(-1);
        }

        if(this.getCentreY() <= this.radius) {
            SoundManager.playClip2();
            this.setCentreY(radius);
            this.vSpeed.multY(-1);
        }

        /*if(this.getCentreY() >= this.fieldHeight - this.radius) {
            this.setCentreY(this.fieldHeight - this.radius);
            this.vSpeed.multY(-1);
        }*/
    }

    public void updateObject() {

    }


    @Override
    public boolean handleObjectCollision(GameObject o) {
        if (!(o instanceof Brick)) {
            return false;
        }

        double ballx = this.getCentreX();
        double bally = this.getCentreY();
        double ballradius = this.getRadius();

        double ox = o.getX();
        double oy = o.getY();
        double ow = o.getW();
        double oh = o.getH();

        // vi tri tren gach gan nhat voi bong
        double closestX = Math.max(ox, Math.min(ballx, ox + ow));
        double closestY = Math.max(oy, Math.min(bally, oy + oh));

        // vecto tu tam bong den vi tri gan nhat cua gach
        double dx = ballx - closestX;
        double dy = bally - closestY;


        if (dx * dx + dy * dy > ballradius * ballradius) {
            return false;
        }

        // neu co var?? chuan hoa vecto de phuc vu tinh phap
        SoundManager.playClip1();

        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) len = 0.001; // tranh chia  0

        // vecto phap tuyen
        double nx = dx / len;
        double ny = dy / len;

        // van toc cho bong
        double vx = this.vSpeed.getX();
        double vy = this.vSpeed.getY();

        // cong thuc phan xa v` = v - 2(n.v)*v;
        double dot = vx * nx + vy * ny;
        double rx = vx - 2 * dot * nx;
        double ry = vy - 2 * dot * ny;

        //set lai van toc cho ball khi var cham voi brick
        this.vSpeed.setX(rx);
        this.vSpeed.setY(ry);

        //
        double overlap = ballradius - Math.sqrt(dx * dx + dy * dy) + 0.5;
        this.setCentreX(this.getCentreX() + nx * overlap);
        this.setCentreY(this.getCentreY() + ny * overlap);


        return true;
    }

}