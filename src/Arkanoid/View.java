package Arkanoid;

import Arkanoid.GameObjects.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class View implements Serializable{
    //private int curState;

    private transient Scene scene;
    private transient Group root;
    private transient Canvas playCanvas;

    private Platform platform;

    private transient Button pauseButton;

    private transient Button resetButton;

    //private Button addBall;
    private Controller.GameState saveThis = Controller.curGameState;

    public Controller.GameState getSaveThis() {
        return saveThis;
    }

    public void setSaveThis(Controller.GameState g) {
        if (g == Controller.GameState.MENU) {
            saveThis = Controller.GameState.PLAYING;
        } else {
            saveThis = g;
        }
    }
    private final int WIDTH = 800;
    private final int HEIGHT = 640;
    private final int PLAY_WIDTH = 550;
    private final int PLAY_HEIGHT = 640;
    private final int BALL_RADIUS = 10;

    private int laserShots = 0;
    private boolean magnet = false;
    private boolean sticky = false;
    private int ballPower = 1;

    private ArrayList<Ball>balls;

    private ArrayList<Brick>bricks;

    private int actualBrickNumber = 0;
    private int actualBallNumber = 0;
    private ArrayList<Bonus>bonuses;

    private transient MapManager mapManager;

    private int curLevel = 1;

    private transient Text curScoreText;

    private transient Text curLevelText;

    private transient Text curLivesText;

    private transient Text highScoreText;

    private transient Text pausedText;

    private transient Font font;

    public View() {
        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.bonuses = new ArrayList<Bonus>();
        //this.curMap = new ArrayList<String>();
        pauseButton = new Button();
        resetButton = new Button();
        //addBall = new Button();
        mapManager = new MapManager();
        initialize(false);
    }

    public Group getRoot() {
        return this.root;
    }

    public void setMagnet(boolean x) {
        this.magnet = x;
    }

    public boolean getMagnet() {
        return this.magnet;
    }

    public void setSticky(boolean x) {
        this.sticky = x;
    }

    public boolean getSticky() {
        return this.sticky;
    }

    public void setBallPower(int x) {
        this.ballPower = x;
    }

    public int getBallPower() {
        return this.ballPower;
    }


    //viết Arkanoid.View(int level) ở đây


    //TAKES IN CURRENT LEVEL TO LOAD IT'S CORRESPONDING MAP
    public View(int level) {
        this.curLevel = level;

        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.bonuses = new ArrayList<Bonus>();
        //this.curMap = new ArrayList<String>();
        //addBall = new Button();
        mapManager = new MapManager();
        initialize(false);
    }

    //level là số thứ tự của level hiện tại

    public void initialize(boolean bool) {
        root = new Group();
        this.playCanvas = new Canvas(800, 640);

        InputStream fontStream = getClass().getResourceAsStream("/fontName.ttf");
        font = Font.loadFont(fontStream, 40);

        this.curScoreText = new Text();
        this.curScoreText.setText("SCORE \n");
        this.curScoreText.setX(570);
        this.curScoreText.setY(110);
        this.curScoreText.setFont(font);

        this.curLevelText = new Text();
        this.curLevelText.setFont(font);
        this.curLevelText.setX(630);
        this.curLevelText.setY(210);
        this.curLevelText.setText("LEVEL \n" + curLevel);

        this.curLivesText = new Text();
        this.curLivesText.setText("LIVES\n");
        this.curLivesText.setX(630);
        this.curLivesText.setY(310);
        this.curLivesText.setFont(font);

        this.highScoreText = new Text();
        this.highScoreText.setFont(font);
        this.highScoreText.setX(690);
        this.highScoreText.setY(70);
        this.highScoreText.setText("HIGH\nSCORE\n");

        this.pausedText = new Text();
        this.pausedText.setFont(Font.font(font.getFamily(), 50));
        this.pausedText.setX(615);
        this.pausedText.setY(500);


        pauseButton = new Button();
        pauseButton.setText("PAUSE");
        pauseButton.setFont(Font.font(font.getFamily(),24));
        pauseButton.setLayoutX(570);
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    pausedText.setText("PAUSED");
                    SoundManager.playClip2();
                    Controller.curGameState = Controller.GameState.PAUSE;
                    saveThis = Controller.GameState.PAUSE;
                    //writeMapData();
                } else if (Controller.curGameState == Controller.GameState.PAUSE) {
                    pausedText.setText("");
                    SoundManager.playClip2();
                    Controller.curGameState = Controller.GameState.PLAYING;
                    saveThis = Controller.GameState.PLAYING;
                }
            }
        });

        resetButton = new Button();
        resetButton.setText("RESET");
        resetButton.setFont(Font.font(font.getFamily(),24));
        resetButton.setLayoutX(690);
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                /*if(Arkanoid.Controller.curGameState == Arkanoid.Controller.GameState.PLAYING) {
                    Arkanoid.Controller.curGameState = Arkanoid.Controller.GameState.RESET;
                    writeMapData();
                }*/
                //if (Controller.curGameState != Controller.GameState.PRE_PLAYING) {
                    SoundManager.playLoseLifeClip();
                    Controller.curGameState = Controller.GameState.PAUSE;
                    pausedText.setText("PAUSED");
                    saveThis = Controller.GameState.PAUSE;

                    Alert resetAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    resetAlert.setTitle("Reset confirmation");
                    resetAlert.setHeaderText("Are you sure you want to reset?");

                    Optional<ButtonType> result = resetAlert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (Controller.curGameState == Controller.GameState.PAUSE) {
                            Controller.curGameState = Controller.GameState.RESET;
                            //writeMapData();
                        }
                    } else {
                        Controller.curGameState = Controller.GameState.PLAYING;
                        saveThis = Controller.GameState.PLAYING;
                        pausedText.setText("");
                    }
                //}
            }
        });


        Canvas otherCanvas = new Canvas(800,640);
        GraphicsContext otherGc = otherCanvas.getGraphicsContext2D();
        otherGc.setFill(Color.DARKGREEN);
        otherGc.fillRect(550,0,800,640);

        root.getChildren().add(otherCanvas);
        root.getChildren().add(playCanvas);
        root.getChildren().add(curScoreText);
        root.getChildren().add(curLevelText);
        root.getChildren().add(curLivesText);
        root.getChildren().add(highScoreText);

        root.getChildren().add(pausedText);

        root.getChildren().add(pauseButton);
        root.getChildren().add(resetButton);
        //root.getChildren().add(platform.getHitBox());


        this.scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                platform.handleEvent(event);
                //platform.move();
            }

        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                platform.handleEvent(event);
                //spawnBall(event);
                launchBall(event);
                shootLaser(event);
                //platform.move();
            }
        });

        if (bool) {

            if (saveThis == Controller.GameState.PAUSE) {
                this.pausedText.setText("PAUSED");
            }

            Rectangle rect = new Rectangle();
            rect.setX(platform.getX());
            rect.setY(640-10);
            rect.setWidth(platform.getW());
            rect.setHeight(10);

            this.platform.setHitBox(rect);
            platform.loadImage();

            for (Brick br : bricks) {
                Rectangle tmp = new Rectangle(br.getX(), br.getY()
                        , br.getW(), br.getH());
                br.setHitBox(tmp);
                br.loadImage();
            }

            for (Ball b : balls) {
                if (!(b instanceof Bullet)) {
                    Circle c = new Circle(b.getCentreX(), b.getCentreY(), b.getRadius());
                    RadialGradient rg = new RadialGradient(
                            0, 0,
                            0.35, 0.35,
                            0.5,
                            true,
                            CycleMethod.NO_CYCLE,
                            new Stop(0.0, Color.WHITE),
                            new Stop(1.0, Color.BLUE)
                    );

                    c.setFill(rg);
                    b.setHitBox(c);
                } else {
                    Circle c1 = new Circle();
                    c1.setCenterX(b.getCentreX());
                    c1.setCenterY(b.getCentreY());
                    c1.setRadius(5);
                    c1.setFill(Color.RED);
                    //Bullet b1 = new Bullet(c1);
                    b.setHitBox(c1);
                }
                //root.getChildren().add()
            }

            for (Ball b : balls) {
                //System.out.println(b.getvSpeed().getX() + " " + b.getvSpeed().getY());
                root.getChildren().add(b.getHitBox());
            }

            for (Bonus bo : bonuses) {
                Rectangle tmp = new Rectangle(bo.getX(), bo.getY()
                        , bo.getW(), bo.getH());
                bo.setHitBox(tmp);
                bo.loadImage();
            }

        } else {
            Rectangle rect = new Rectangle();
            rect.setX(100);
            rect.setY(640-10);
            rect.setWidth(150);
            rect.setHeight(10);

            ArrayList<String>tmpMap = mapManager.loadMapIntoArr(curLevel);

            Circle c = new Circle(175, 640-22, 10);
            RadialGradient rg = new RadialGradient(
                    0, 0,
                    0.35, 0.35,
                    0.5,
                    true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.WHITE),
                    new Stop(1.0, Color.BLUE)
            );
            c.setFill(rg);
            actualBallNumber+=1;

            Ball b = new Ball(c);
            b.setSpeed(new PVector(0,0));

            balls.add(b);
            this.root.getChildren().add(b.getHitBox());

            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 11; j++) {

                    if (tmpMap.get(i).charAt(j) == '5') {
                        int x = j * 50;
                        int y = i * 30;
                        Rectangle tmp = new Rectangle(x, y, 50, 30);

                        Brick tmpBr = new Brick(tmp, false);
                        this.bricks.add(tmpBr);
                    } else if (tmpMap.get(i).charAt(j) != '0') {
                        int x = j * 50;
                        int y = i * 30;
                        Rectangle tmp = new Rectangle(x, y, 50, 30);

                        Brick tmpBr = new Brick(tmp, tmpMap.get(i).charAt(j) - '0');
                        this.bricks.add(tmpBr);
                        actualBrickNumber++;
                    }
                }
            }

            this.platform = new Platform(rect);
        }

        root.requestFocus();
    }

    public void show(Stage stage, Scene scene) {
        stage.setTitle("ARKANOID");
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return this.scene;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public ArrayList<Brick>getBricks() {
        return this.bricks;
    }

    public int getActualBrickNumber() {
        return this.actualBrickNumber;
    }

    public void setActualBrickNumber(int x) {
        this.actualBrickNumber = x;
    }

    public ArrayList<Bonus>getBonuses() {
        return this.bonuses;
    }

    public void removeFromWorld(Brick brick) {
        //brick.reduceDensity();
        if (brick.getDensity() <= 0 && brick.isBreakable()) {
            this.root.getChildren().remove(brick.getHitBox());
            this.bricks.remove(brick);
            this.actualBrickNumber--;
            //this.root.getChildren().remove(brick.getHitBox());
        }
    }

    public ArrayList<Ball>getBalls() {
        return balls;
    }

    public int getActualBallNumber() {
        return this.actualBallNumber;
    }

    public void setActualBallNumber(int x) {
        this.actualBallNumber = x;
    }

    //THERE'S NO DIFFERENCE BETWEEN BIG AND SMALL BALL


    public void setBall() {
        Circle c = new Circle(platform.getX() + platform.getW()/2, 640-22, 10);
        RadialGradient rg = new RadialGradient(
                0, 0,
                0.35, 0.35,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.WHITE),
                new Stop(1.0, Color.BLUE)
        );
        c.setFill(rg);
        actualBallNumber+=1;

        Ball b = new Ball(c);
        b.setSpeed(new PVector(0,0));

        balls.add(b);
        this.root.getChildren().add(b.getHitBox());
    }

    public void removeBall(Ball b) {
        this.balls.remove(b);
        this.root.getChildren().remove(b.getHitBox());
        if (b instanceof Bullet){
            return;
        }
        this.actualBallNumber--;

    }

    public void launchBall(KeyEvent e) {
        if(Controller.curGameState == Controller.GameState.PLAYING
                && (e.getCode() == KeyCode.W  || e.getCode() == KeyCode.UP)) {
            for (Ball b : balls) {
                if (Objects.equals(b.getSpeed(), new PVector(0, 0))) {
                /*double aa = -3.055052212747844;
                double aaa = -3.6132047217868;*/
                    int tmp = 1;
                    if ( platform.getX() + platform.getW() / 2 <= 225) {
                        tmp = -1;
                    }
                    b.setSpeed(new PVector(-3.055052212747844 * tmp, -3.6132047217868));
                    break;
                }
            }
        }
    }

    public void setLaserShots(int x) {
        laserShots = x;
    }

    public void shootLaser(KeyEvent e) {

        if (Controller.curGameState == Controller.GameState.PLAYING) {
            if (laserShots > 0) {
                if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                    Circle c1 = new Circle();
                    c1.setCenterX(platform.getX() + 5);
                    c1.setCenterY(platform.getY() - 10);
                    c1.setRadius(5);
                    c1.setFill(Color.RED);
                    Bullet b1 = new Bullet(c1);

                    Circle c2 = new Circle();
                    c2.setCenterX(platform.getX() + platform.getW() - 5);
                    c2.setCenterY(platform.getY() - 10);
                    c2.setRadius(5);
                    c2.setFill(Color.RED);
                    Bullet b2 = new Bullet(c2);
                    //b2.setSpeed(balls.get(0).getSpeed());
                    balls.add(b1);
                    balls.add(b2);
                    root.getChildren().add(b1.getHitBox());
                    root.getChildren().add(b2.getHitBox());
                    laserShots -= 1;
                }
            }
        }
    }

    public void render() {
        GraphicsContext gc = playCanvas.getGraphicsContext2D();
        this.playCanvas.getGraphicsContext2D().clearRect(0,0,800, 640);

        this.platform.render(gc);

        for (Bonus bo:bonuses) {
            bo.render(gc);
        }
        for(Brick br : bricks) {
            br.render(gc);
        }
    }

    public void setCurScoreText(int curScore) {
        this.curScoreText.setText("SCORE \n" + curScore);
    }

    public void setCurLivesText(int curLives) {
        this.curLivesText.setText("LIVES \n" + curLives);
    }

    public void setHighScoreText(int highScore) {
        this.highScoreText.setText("HIGH\nSCORE\n" + highScore);
    }

    public void spawnBonus(double x, double y, int type) {
        Rectangle tmp = new Rectangle(x,y,50,30);
        tmp.setFill(Color.RED);
        tmp.setStroke(Color.BLACK);
        tmp.setStrokeWidth(3);
        Bonus bonus = new Bonus(tmp, type);
        this.bonuses.add(bonus);
        //this.root.getChildren().add(bonus.getHitBox());
    }

    public void removeBonus(Bonus bonus) {
        this.bonuses.remove(bonus);
        //this.root.getChildren().remove(bonus.getHitBox());
    }
}