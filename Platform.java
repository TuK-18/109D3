import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Platform extends GameObject{
    private int xVelocity;

    private final int PLATFORM_VEL = 10;

    //private int lives;

    private Image platform1Img;
    private Image platform2Img;
    private Image platform4Img;

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


    public int getxVelocity() {
        return xVelocity;
    }


    /*public void moveLeft() {
        xVelocity-=PLATFORM_VEL;
        this.setX(this.getX() + xVelocity);
        xVelocity=0;
        System.out.println("LEFT");
    }

    public void moveRight() {
        xVelocity+=PLATFORM_VEL;
        this.setX(this.getX() + xVelocity);
        xVelocity=0;
        System.out.println("RIGHT");
    }*/



    public void handleEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            
            switch (e.getCode()) {
                case A:
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
                    if(x > 0) {
                        xVelocity = 0;
                        xVelocity -= PLATFORM_VEL;
                    }
                    break;
                case RIGHT:
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
                    xVelocity = 0;
                    break;
                case RIGHT:
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

    /*public boolean detectCollision(Bonus bonus) {

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
}