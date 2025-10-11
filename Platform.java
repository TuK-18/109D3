import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;

public class Platform extends GameObject{
    private int xVelocity;

    private final int PLATFORM_VEL = 50;

    public Platform (Rectangle shape) {
        super(shape);
        /*this.setX(x);
        this.setY(y);
        this.setWidth(w);
        this.setHeight(h);
        this.setFill(color);*/
    }

    /*public void handleEvent(KeyEvent event) {

    }*/



    public void moveLeft() {
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
    }

    public void handleEvent(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED ) {
            System.out.println("vhvhv");
            switch (e.getCode()) {
                case A:
                    //xVelocity -= PLATFORM_VEL;

                    this.setX(this.getX() - PLATFORM_VEL);

                    if (this.x  < 0 || this.x + this.w > 600) {
                        this.setX(this.getX() + PLATFORM_VEL);
                    }
                    break;
                case D:
                    //xVelocity += PLATFORM_VEL;
                    this.setX(this.getX() + PLATFORM_VEL);

                    if (this.x  < 0 || this.x + this.w > 600) {
                        this.setX(this.getX() - PLATFORM_VEL);
                    }
                    break;
                case KeyCode.LEFT:
                    this.setX(this.getX() - PLATFORM_VEL);

                    if (this.x  < 0 || this.x + this.w > 600) {
                        this.setX(this.getX() + PLATFORM_VEL);
                    }
                    break;
                case KeyCode.RIGHT:
                    this.setX(this.getX() + PLATFORM_VEL);

                    if (this.x  < 0 || this.x + this.w > 600) {
                        this.setX(this.getX() - PLATFORM_VEL);
                    }
                    break;
                default:
                    break;
            }
        } /*else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (e.getCode()) {
                case A:
                    xVelocity += PLATFORM_VEL;
                    break;
                case D:
                    xVelocity -= PLATFORM_VEL;
                    break;
                case KeyCode.LEFT:
                    xVelocity += PLATFORM_VEL;
                    break;
                case KeyCode.RIGHT:
                    xVelocity -= PLATFORM_VEL;
                    break;
                default:
                    break;
            }
        }*/
    }

    /*public void move() {
        this.setX(this.getX() + PLATFORM_VEL);

        if (this.x  < 0 || this.x + this.w > 640) {
            this.setX(this.getX() - PLATFORM_VEL);
        }
        //return;
    }*/
}