

import javafx.event.EventHandler;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import org.w3c.dom.css.Rect;

import static java.awt.event.KeyEvent.KEY_PRESSED;

//nhận vào tham số là curLevel để load map của level hiện tại
//bằng method trong MapManager

public class View {
    //private Data data;
    private Scene scene;
    private Group root;
    private Ball b1;
    private Ball b2;
    private Ball b3;
    private Ball b4;
    private Ball b5;
    private Ball b6;
    private Platform platform;
    private Brick br1;
    private Brick br2;

    private final int WIDTH = 600;
    private final int HEIGHT = 640;
    private final int BALL_SIZE = 10;
    private ArrayList<GameObject>objects;

    private ArrayList<Ball>balls;
    private ArrayList<Brick>bricks;

    private ArrayList<String>curMap;


    public View() {
        //this.data = d;
        this.objects = new ArrayList<GameObject>();
        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.curMap = new ArrayList<String>();
        initialize();
    }

    //viết View(int level) ở đây

    //Viết initialize(int level) ở đây
    //level là số thứ tự của level hiện tại

    public void initialize() {
        root = new Group();
        Circle onlyBall = new Circle();
        onlyBall.setCenterX(150);
        onlyBall.setCenterY(540-10);
        onlyBall.setRadius(10);
        onlyBall.setFill(Color.YELLOW);

        Circle circle2 = new Circle(400,600,10,Color.BLACK);

        Rectangle rect = new Rectangle();
        rect.setX(100);
        rect.setY(640-10);
        rect.setWidth(150);
        rect.setHeight(10);
        rect.setFill(Color.GREEN);

        Rectangle bRect = new Rectangle(100,10,100,100);
        bRect.setFill(Color.BLUE);

        Rectangle bR1 = new Rectangle(500,10,100,100);
        bR1.setFill(Color.RED);
        /*ArrayList<String>tmpMap = new ArrayList<String>(Arrays.asList(
                "000000000000",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "011100011100",
                "000000000000"
        ));*/

        ArrayList<String>tmpMap = new ArrayList<String>(Arrays.asList(
                "00010001000",
                "00001010000",
                "00001010000",
                "00011111000",
                "00011111000",
                "00111111100",
                "00111111100",
                "01111111110",
                "01111111110",
                "01011111010",
                "01010001010",
                "01010001010",
                "00001010000"
        ));



        /*for(int r = 0; r < 360; r+= 30) {
            for (int c = 50; c < 550; c += 50) {
                Rectangle tmp = new Rectangle(c, r, 50, 30);
                tmp.setFill(Color.RED);
                tmp.setStroke(Color.BLACK);
                tmp.setStrokeWidth(3);
                Brick tmpBr = new Brick(tmp);
                this.bricks.add(tmpBr);
            }
        }*/

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 11; j++) {
                if(tmpMap.get(i).charAt(j) == '1'){
                    int x = j * 50;
                    int y = i * 30;
                    Rectangle tmp = new Rectangle(x, y, 50, 30);
                    tmp.setFill(Color.RED);
                    tmp.setStroke(Color.BLACK);
                    tmp.setStrokeWidth(3);
                    Brick tmpBr = new Brick(tmp);
                    this.bricks.add(tmpBr);
                }

            }
        }

        this.platform = new Platform(rect);
        /*this.br1 = new Brick(bRect);
        this.br2 = new Brick(bR1);*/

        /*this.b2 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b3 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b4 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b5 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b6 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);*/

        /*this.b1 = new Ball(onlyBall);
        this.b2 = new Ball(circle2);*/

        /*this.objects.add(b1);
        this.objects.add(b2);
        this.objects.add(platform);*/

        /*this.balls.add(b1);
        this.balls.add(b2);*/

        /*this.bricks.add(br1);
        this.bricks.add(br2);*/

        /*this.balls.add(b2);
        this.balls.add(b3);
        this.balls.add(b4);
        this.balls.add(b5);
        this.balls.add(b6);*/
        //root.getChildren().add(b1.getHitBox());
        //root.getChildren().add(b2.getHitBox());
        root.getChildren().add(platform.getHitBox());
        /*root.getChildren().add(br1.getHitBox());
        root.getChildren().add(br2.getHitBox());*/

        for (Brick br : bricks) {
            root.getChildren().add(br.getHitBox());
        }
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
                spawnBall(event);
                //platform.move();
            }
        });

        /*scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                spawnBall();
            }
        });*/


    }

    public void show(Stage stage, Scene scene) {
        stage.setTitle("BALLS");
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return this.scene;

    }

    public ArrayList<Ball>getBalls() {
        return balls;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public ArrayList<Brick>getBricks() {
        return this.bricks;
    }

    public void removeFromWorld(Brick brick) {
        brick.reduceDensity();
        if(brick.getDensity()==0) {
            root.getChildren().remove(brick.getHitBox());
            this.bricks.remove(brick);
        }
    }

    public void spawnBall(KeyEvent e) {
        if(this.balls.isEmpty() && e.getCode()== KeyCode.SPACE) {
            Circle c1 = new Circle();
            c1.setCenterX(platform.getX() + 50);
            c1.setCenterY(640 - 10);
            c1.setRadius(10);
            c1.setFill(Color.YELLOW);
            Ball b = new Ball(c1);
            this.balls.add(b);
            root.getChildren().add(b.getHitBox());
        }
    }



}