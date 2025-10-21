

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.w3c.dom.css.Rect;

import static java.awt.event.KeyEvent.KEY_PRESSED;

//nhận vào tham số là curLevel để load map của level hiện tại
//bằng method trong MapManager

public class View {
    //private Data data;
    private Scene scene;
    private Group root;
    private Canvas playCanvas;

    private Platform platform;

    private Button pauseButton;

    private Button loseButton;

    private Button addBall;

    private final int WIDTH = 600;
    private final int HEIGHT = 640;
    private final int BALL_SIZE = 10;
    private ArrayList<GameObject>objects;

    private ArrayList<Ball>balls;

    private ArrayList<Brick>bricks;
    private int actualBrickNumber = 0;

    private ArrayList<Bonus>bonuses;

    private ArrayList<String>curMap;

    private MapManager mapManager;

    private int curLevel = 1;

    private Text curScoreText;

    private Text curLevelText;

    public View() {
        //this.data = d;
        this.objects = new ArrayList<GameObject>();
        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.bonuses = new ArrayList<Bonus>();
        this.curMap = new ArrayList<String>();
        pauseButton = new Button();
        loseButton = new Button();
        addBall = new Button();
        mapManager = new MapManager();
        initialize();
    }

    //viết View(int level) ở đây

    public View(int level) {
        this.curLevel = level;
        this.objects = new ArrayList<GameObject>();
        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.bonuses = new ArrayList<Bonus>();
        this.curMap = new ArrayList<String>();
        pauseButton = new Button();
        loseButton = new Button();
        addBall = new Button();
        mapManager = new MapManager();
        initialize();
    }

    //Viết initialize(int level) ở đây

    /*public void intialize(int level) {
        //this.curMap =
    }*/

    //level là số thứ tự của level hiện tại

    public void initialize() {
        root = new Group();
        this.playCanvas = new Canvas(600, 640);

        this.curScoreText = new Text();
        this.curScoreText.setX(700);
        this.curScoreText.setY(100);

        this.curLevelText = new Text();
        this.curLevelText.setX(700);
        this.curLevelText.setY(200);
        this.curLevelText.setText("LEVEL \n" + curLevel);

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

        /*ArrayList<String>tmpMap = new ArrayList<String>(Arrays.asList(
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
        ));*/

        ArrayList<String>tmpMap = mapManager.loadMapIntoArr(curLevel);

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
                /*if(tmpMap.get(i).charAt(j) == '1'){
                    int x = j * 50;
                    int y = i * 30;
                    Rectangle tmp = new Rectangle(x, y, 50, 30);
                    tmp.setFill(Color.RED);
                    tmp.setStroke(Color.BLACK);
                    tmp.setStrokeWidth(3);
                    Brick tmpBr = new Brick(tmp);
                    this.bricks.add(tmpBr);
                }*/
                /*switch (tmpMap.get(i).charAt(j)) {
                    //case '1'
                }*/

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

        pauseButton = new Button();
        pauseButton.setText("PAUSE");
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.PAUSE;
                } else if (Controller.curGameState == Controller.GameState.PAUSE) {
                    Controller.curGameState = Controller.GameState.PLAYING;
                }
            }
        });

        loseButton.setText("LOSE");
        loseButton.setLayoutX(400);
        loseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.LOSE;
                }
            }
        });

        addBall.setText("add ball");
        addBall.setLayoutX(500);
        addBall.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    //Controller.curGameState = Controller.GameState.MENU;
                    Circle c2 = new Circle();
                    c2.setCenterY(20);
                    c2.setCenterX(20);
                    c2.setRadius(10);
                    c2.setFill(Color.BLUE);
                    Ball b2 = new Ball(c2);
                    balls.add(b2);
                    root.getChildren().add(b2.getHitBox());
                }
            }
        });

        root.getChildren().add(playCanvas);
        root.getChildren().add(curScoreText);
        root.getChildren().add(curLevelText);

        root.getChildren().add(addBall);

        root.getChildren().add(pauseButton);
        root.getChildren().add(loseButton);
        root.getChildren().add(platform.getHitBox());

        /*root.getChildren().add(br1.getHitBox());
        root.getChildren().add(br2.getHitBox());*/

        /*for (Brick br : bricks) {
            root.getChildren().add(br.getHitBox());
        }*/
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

    public int getActualBrickNumber() {
        return this.actualBrickNumber;
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

    public void spawnBall(KeyEvent e) {
        //System.out.println("gghg");
        if(this.balls.isEmpty() && e.getCode() == KeyCode.W) {
            Controller.curGameState = Controller.GameState.PLAYING;
            Circle c1 = new Circle();
            c1.setCenterX(platform.getX() + 50);
            c1.setCenterY(640 - 10);
            c1.setRadius(10);
            c1.setFill(Color.YELLOW);
            Ball b = new Ball(c1);

            /*Circle c2 = new Circle();
            c2.setCenterY(20);
            c2.setCenterX(20);
            c2.setRadius(10);
            c2.setFill(Color.BLUE);
            Ball b2 = new Ball(c2);*/

            //this.balls.add(b2);

            this.balls.add(b);
            this.root.getChildren().add(b.getHitBox());

            //System.out.println("gghg");
            //this.root.getChildren().add(b2.getHitBox());
        }
    }

    public void removeBall(Ball b) {
        this.balls.remove(b);
        this.root.getChildren().remove(b.getHitBox());
        this.playCanvas.getGraphicsContext2D().clearRect(b.getX(), b.getY(), 50, 30);
    }

    public void render() {
        GraphicsContext gc = playCanvas.getGraphicsContext2D();
        this.playCanvas.getGraphicsContext2D().clearRect(0,0,640, 640);

        //this.platform.render(gc);

        /*for(Ball b : balls) {
            b.render(gc);
        }*/

        for(Brick br : bricks) {
            br.render(gc);
        }
    }

    public void setCurScoreText(int curScore) {
        this.curScoreText.setText("SCORE \n" + curScore);
    }

    public void spawnBonus(double x, double y, int type) {
        Rectangle tmp = new Rectangle(x,y,50,30);
        tmp.setFill(Color.RED);
        tmp.setStroke(Color.BLACK);
        tmp.setStrokeWidth(3);
        Bonus bonus = new Bonus(tmp, type);
        this.bonuses.add(bonus);
        this.root.getChildren().add(bonus.getHitBox());
    }

    public void removeBonus(Bonus bonus) {
        this.bonuses.remove(bonus);
        this.root.getChildren().remove(bonus.getHitBox());
    }

    /*public void handleBonus(int type) {

    }*/

}