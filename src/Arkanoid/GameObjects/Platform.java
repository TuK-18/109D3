package Arkanoid.GameObjects;

import Arkanoid.SoundManager;
import javafx.scene.shape.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Platform extends GameObject {
    private int xVelocity;

    private final int PLATFORM_VEL = 10;

    //private int lives;

    private transient Image platform1Img;
    private transient Image platform2Img;
    private transient Image platform4Img;

    public Platform (Rectangle shape) {

        super(shape);
        /*this.setX(x);
        this.setY(y);
        this.setWidth(w);
        this.setHeight(h);
        this.setFill(color);*/
        //this.lives = 5;

        URL imagePath = Platform.class.getResource("/platform3.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        URL platform1Path = Platform.class.getResource("/platform1.png");
        assert platform1Path != null;
        this.platform1Img = new Image(platform1Path.toExternalForm());

        URL platform2Path = Platform.class.getResource("/platform2.png");
        assert platform2Path != null;
        this.platform2Img = new Image(platform2Path.toExternalForm());

        URL platform4Path = Platform.class.getResource("/platform4.png");
        assert platform4Path != null;
        this.platform4Img = new Image(platform4Path.toExternalForm());
    }

    public void loadImage() {
        URL imagePath = Platform.class.getResource("/platform3.png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());

        URL platform1Path = Platform.class.getResource("/platform1.png");
        assert platform1Path != null;
        this.platform1Img = new Image(platform1Path.toExternalForm());

        URL platform2Path = Platform.class.getResource("/platform2.png");
        assert platform2Path != null;
        this.platform2Img = new Image(platform2Path.toExternalForm());

        URL platform4Path = Platform.class.getResource("/platform4.png");
        assert platform4Path != null;
        this.platform4Img = new Image(platform4Path.toExternalForm());
    }


    public int getxVelocity() {
        return xVelocity;
    }


    public void handleEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            //System.out.println("vhvhv");
            switch (e.getCode()) {
                case A:
                    //System.out.println("a");
                    if(x > 0) {
                        xVelocity = 0;
                        xVelocity -= PLATFORM_VEL;
                    }
                    /*this.setX(this.getX() - PLATFORM_VEL);

                    if (this.getX()  < 0) {
                        this.setX(this.getX() + PLATFORM_VEL);
                    }*/
                    //xVelocity = 0;
                    //xVelocity -= PLATFORM_VEL;
                    break;
                case D:
                    //System.out.println("d");
                    if(x + this.getW() < 550) {
                        xVelocity = 0;
                        xVelocity += PLATFORM_VEL;
                    }

                    /*this.setX(this.getX() + PLATFORM_VEL);

                    if (this.getX() + this.getW() > 600) {
                        this.setX(this.getX() - PLATFORM_VEL);

                    }*/
                    break;
                case LEFT:
                    //System.out.println("left k");
                    if(x > 0) {
                        xVelocity = 0;
                        xVelocity -= PLATFORM_VEL;
                    }
                    break;
                case RIGHT:
                    //.out.println("right k");
                    if(x + this.getW() < 550) {
                        xVelocity = 0;
                        xVelocity += PLATFORM_VEL;
                    }
                    break;
                default:
                    break;
            }

        } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (e.getCode()) {
                case A:
                    /*this.setX(this.getX() - PLATFORM_VEL);

                    if (this.getX() < 0) {
                        this.setX(this.getX() + PLATFORM_VEL);
                    }*/
                    //xVelocity = 0;
                    //xVelocity += PLATFORM_VEL;
                    xVelocity = 0;
                    break;
                case D:
                    //xVelocity -= PLATFORM_VEL;
                    xVelocity = 0;
                    /*this.setX(this.getX() + PLATFORM_VEL);

                    if (this.getW() + this.getW() > 600) {
                        this.setX(this.getX() - PLATFORM_VEL);
                    }*/
                    break;
                case LEFT:
                    //System.out.println("lglglllll");
                    xVelocity = 0;
                    break;
                case RIGHT:
                    //System.out.println("ffrrrrrrrr");
                    xVelocity = 0;
                    break;
                default:
                    break;
            }
        }
    }

    public void move() {

        //System.out.println(xVelocity);

        this.setX(this.getX() + xVelocity);

        if (this.getX() + this.getW() > 550) {
            this.setX(this.getX() - xVelocity);
        }

        if(this.getX() < 0) {
            this.setX(this.getX() - xVelocity);
        }
        //xVelocity = 0;
        //return;
    }

    /*public boolean detectCollision(Arkanoid.GameObjects.Bonus bonus) {

    }*/

    @Override
    public void render(GraphicsContext gc) {
        switch ((int) this.getW()) {
            case 50:
                gc.drawImage(platform1Img,this.getX(), this.getY());
                break;
            case 100:
                gc.drawImage(platform2Img, this.getX(), this.getY());
                break;
            case 150:
                gc.drawImage(image, this.getX(), this.getY());
                break;
            case 200:
                gc.drawImage(platform4Img, this.getX(), this.getY());
                break;
        }
    }

    @Override
    public boolean handleObjectCollision(GameObject b ) {
        if (!(b instanceof Ball)) {
            return false;
        }

        Ball ball = (Ball) b;
        // thuoc tinh tron

        double bx = ball.getCentreX();
        double by = ball.getCentreY();
        double br = ball.getRadius();

        // thuoc tinh cua platform
        double px = this.getX();
        double py = this.getY();
        double pw = this.getW();
        double ph = this.getH();

        // neu khogn co va cham thi thoi
        if (bx + br < px ||
                bx - br > px + pw ||
                by + br < py ||
                by - br > py + ph){

            return false;
        }

        SoundManager.playClip3();

        // fix ket vao this
        ball.setCentreY(py - br - 0.5);

        double thisCenter = px + pw / 2.0;
        // dua ve gia tri co boi la
        double offset = (bx - thisCenter) / (pw / 2.0);
        // chuan hoa ve gia tri trong khoang -1- 1
        offset = Math.max(-1, Math.min(1, offset));

        // goc lech toi da
        double maxAngle = Math.toRadians(70);
        double angle = offset * maxAngle;

        // get speed
        double speed =  ball.getScalarSpeed();

        // set lai huong
        double newVx = speed * Math.sin(angle);
        double newVy = -Math.abs(speed *   Math.cos(angle));

        // van toc cau plat + van toc bong
        if (this.vSpeed != null) {
            newVx += this.vSpeed.getX() * 0.2;
        }

        // Option: giới hạn vx để tránh quá nghiêng
        double maxHorizontal = speed * 0.95;
        if (newVx > maxHorizontal) newVx = maxHorizontal;
        if (newVx < -maxHorizontal) newVx = -maxHorizontal;

        // Áp dụng lại vận tốc cho bóng
        ball.vSpeed.setX(newVx);
        ball.vSpeed.setY(newVy);
        return true;
    }
}