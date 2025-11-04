package Arkanoid.GameObjects;

import Arkanoid.View;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

import javafx.scene.image.Image;

import java.net.URL;

public class Bonus extends Brick {

    private int yVelocity = 5;

    private int type;

    public Bonus(Rectangle rect, int type) {
        super(rect);
        this.type = type;

        /*URL imagePath = Bonus.class.getResource("/bonus" + type+ ".png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());*/
        loadImage();
    }

    public int getType() {
        return this.type;
    }

    public void move() {
        this.setY(this.getY() + yVelocity);
    }

    public boolean detectCollision(GameObject platform) {
        if (platform.getHitBox().intersects(this.getHitBox().getLayoutBounds())){
            if (this.getX() < platform.getX() + platform.getW() - 5) {
                return true;
            }

        }
        return false;
    }

    public void move(double x, double y) {
        if (this.type == 4
                || this.type == 6
                || this.type == 9
                || this.type == 11
                || this.type == 5) {
            this.move();
        } else {
            PVector speed = new PVector(x - this.getX(), y - this.getY());
            speed.normalize();
            speed.mult(3);

            this.setX(this.getX() + speed.getX());
            this.setY(this.getY() + speed.getY());
        }
    }

    public void handleBonus(View view) {

        switch (this.type) {
            case 1:
                //ADD BALL
                if (!view.getBalls().isEmpty()) {
                    if (view.getBalls().size() == 10 || view.getBalls().isEmpty()) {
                        return;
                    }
                    RadialGradient rg = new RadialGradient(
                            0,0,
                            0.35,0.35,
                            0.5,
                            true,
                            CycleMethod.NO_CYCLE,
                            new Stop(0.0,Color.WHITE),
                            new Stop(1.0,Color.BLUE)
                    );

                    Circle c2 = new Circle();
                    c2.setCenterX(view.getBalls().get(0).getCentreX() + 30);
                    c2.setCenterY(view.getBalls().get(0).getCentreY() + 30);
                    c2.setRadius(10);
                    c2.setFill(rg);
                    Ball b2 = new Ball(c2);
                    //b2.setSpeed(balls.get(0).getSpeed());
                    view.getBalls().add(b2);
                    view.getRoot().getChildren().add(b2.getHitBox());

                    Circle c3 = new Circle();
                    c3.setCenterX(view.getBalls().get(0).getCentreX() - 30);
                    c3.setCenterY(view.getBalls().get(0).getCentreY() - 30);
                    c3.setRadius(10);
                    c3.setFill(rg);
                    Ball b3 = new Ball(c3);
                    view.getBalls().add(b3);
                    view.getRoot().getChildren().add(b3.getHitBox());
                    view.setActualBallNumber(view.getActualBallNumber() + 2);
                }
                break;
            case 2:
                //ADD 100 POINTS
                break;
            case 3:
                view.setSticky(true);
                break;
            case 4:
                //this.curPoint -= 500;
                break;
            case 5:
                //FAST FORWARD TO THE NEXT LEVEL
                /*curLevel+=1;
                levelUp();*/
                view.setActualBrickNumber(0);
                break;
            case 6:
                //FAST BALLS
                for (Ball b : view.getBalls()) {
                    //b.setSpeed(b.getSpeed().mult(2));
                    if (Math.abs(b.getSpeed().getX()) * 1.5 < 6
                            && Math.abs(b.getSpeed().getY()) * 1.5 < 6) {

                        b.getSpeed().mult(1.5);
                    }
                }
                break;
            case 7:
                //LONG PLATFORM
                if (view.getPlatform().getW() <= 150 ) {
                    if(view.getPlatform().getX() + view.getPlatform().getW() + 50 >= 550){
                        view.getPlatform().setX(view.getPlatform().getX() - 50);
                    }
                    view.getPlatform().setW(view.getPlatform().getW() + 50);
                }
                break;
            case 8:
                //SHORT PLATFORM
                if (view.getPlatform().getW() >= 150 ) {
                    view.getPlatform().setW(view.getPlatform().getW() - 50);
                }
                break;
            case 9:
                //INSTA DEATH
                /*loseSceneController.fadesIn();
                curGameState = Arkanoid.Controller.GameState.LOSE;*/
                break;
            case 10:
                //+1 LIVE
                /*this.curLives += 1;
                writeData();*/
                break;
            case 11:
                //SLOW BALLS
                for (Ball b : view.getBalls()) {
                    //b.setSpeed(b.getSpeed().mult(2));
                    if (Math.abs(b.getSpeed().getX()) * 0.5 > 1.5
                            && Math.abs(b.getSpeed().getY()) * 0.5 > 1.5) {
                        b.getSpeed().mult(0.5);
                    }
                }
                break;
            case 12:
                //LOAD THE LASER
                //laser = true;
                //view.shootLaser(laser);
                view.setLaserShots(3);
                break;
            case 13:
                //BONUSES FLY TOWARDS PLATFORM
                //magnet = true;
                view.setMagnet(true);
                break;

            case 14:
                //MAKE BALL STRONGER
                //this.ballPower = 2;
                view.setBallPower(2);
                break;
        }
    }

    public void loadImage() {
        URL imagePath = Bonus.class.getResource("/bonus" + type+ ".png");
        assert imagePath != null;
        this.image = new Image(imagePath.toExternalForm());
    }

}