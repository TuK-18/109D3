

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ModuleLayer.Controller;

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
    private ArrayList<Bonus> bonuses;
    private ArrayList<Brick>bricks;

    private int actualBrickNumber = 0;

    public ArrayList<Bonus>getBonuses() {
        return this.bonuses;
    }


    private ArrayList<String>curMap;

    private MapManager mapManager;

    private int curLevel = 1;

    private Text curScoreText;

    private Text curLevelText;

    private Text curLivesText;

    private Text highScoreText;

    private Font font;

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

        initialize();
    }

    public View(int level) {
        this.curLevel = level;
        this.objects = new ArrayList<GameObject>();
        this.balls = new ArrayList<Ball>();
        this.bricks = new ArrayList<Brick>();
        this.bonuses = new ArrayList<Bonus>();
        this.curMap = new ArrayList<String>();
        pauseButton = new Button();
        resetButton = new Button();
        addBall = new Button();

        mapManager = new MapManager();

        try {
            File file = new File("res/map" + curLevel + ".data");

            FileInputStream fileInputStream = new FileInputStream(file);

            ObjectInputStream os = new ObjectInputStream(fileInputStream);

            mapManager = (MapManager) os.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("The first play");
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            
        }

        InputStream fontStream = getClass().getResourceAsStream("fontName.ttf");
        font = Font.loadFont(fontStream, 40);
        initialize();
    }



    //Viết initialize(int level) ở đây

    /*public void intialize(int level) {
        //this.curMap =
    }*/

    //level là số thứ tự của level hiện tại

    public void initialize() {
        root = new Group();
        this.playCanvas = new Canvas(800, 640);

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
        pauseButton.setFont(Font.font(font.getFamily(),24));
        pauseButton.setLayoutX(560);
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.PAUSE;
                    writeMapData();
                } else if (Controller.curGameState == Controller.GameState.PAUSE) {
                    Controller.curGameState = Controller.GameState.PLAYING;
                }
            }
        });

        resetButton.setText("RESET");
        resetButton.setFont(Font.font(font.getFamily(),24));
        resetButton.setLayoutX(680);
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                /*if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.RESET;
                    writeMapData();
                }*/
                if (Controller.curGameState != Controller.GameState.PRE_PLAYING) {
                    Controller.curGameState = Controller.GameState.PAUSE;
                    writeMapData();
                    Alert resetAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    resetAlert.setTitle("Reset confirmation");
                    resetAlert.setHeaderText("Are you sure you want to reset?");

                    Optional<ButtonType> result = resetAlert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (Controller.curGameState == Controller.GameState.PAUSE) {
                            Controller.curGameState = Controller.GameState.RESET;
                            writeMapData();
                        }
                    } else {
                        Controller.curGameState = Controller.GameState.PLAYING;
                    }
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

                    /*Circle c2 = new Circle();
                    c2.setCenterY(20);
                    c2.setCenterX(20);
                    c2.setRadius(10);
                    c2.setFill(Color.BLUE);
                    Ball b2 = new Ball(c2);
                    balls.add(b2);
                    root.getChildren().add(b2.getHitBox());*/
                    addBall();
                    /*if (platform.getW() <= 150 ) {
                        if(platform.getX() + platform.getW() + 50 >= 550){
                            platform.setX(platform.getX() - 50);
                        }
                        platform.setW(platform.getW() + 50);
                    }*/
                }
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

        root.getChildren().add(addBall);

        root.getChildren().add(pauseButton);
        root.getChildren().add(resetButton);
        //root.getChildren().add(platform.getHitBox());

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
            }
        });


        root.requestFocus();

    }

    public void writeMapData() {
        try{
            File file = new File("res/map" + curLevel + ".data");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fileOutputStream);
            os.writeObject(mapManager);
            os.close();
        } catch (IOException e) {
            System.out.println("Write object exception");
            e.printStackTrace();
        }
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
            this.balls.add(b);
            this.root.getChildren().add(b.getHitBox());

        }
    }

    public void modifyBallSpeed(double x) {

        if (x == 1.5) {
            for (Ball b : balls) {
                //b.setSpeed(b.getSpeed().mult(2));
                if (Math.abs(b.getSpeed().getX()) * x < 6
                        && Math.abs(b.getSpeed().getY()) * x < 6) {

                    b.getSpeed().mult(x);
                }
            }
        } else if (x == 0.5) {
            for (Ball b : balls) {
                //b.setSpeed(b.getSpeed().mult(2));
                if (Math.abs(b.getSpeed().getX()) * x > 1.5
                        && Math.abs(b.getSpeed().getY()) * x > 1.5) {
                    b.getSpeed().mult(x);
                }
            }
        }
    }
    public void launchBall(KeyEvent e) {
        if(e.getCode() == KeyCode.W) {
            for (Ball b : balls) {
                if (Objects.equals(b.getSpeed(), new PVector(0, 0))) {
                /*double aa = -3.055052212747844;
                double aaa = -3.6132047217868;*/

                    b.setSpeed(new PVector(-3.055052212747844, -3.6132047217868));
                }
            }
        }
    }

    public void removeBall(Ball b) {
        this.balls.remove(b);
        this.root.getChildren().remove(b.getHitBox());
    }
    public void addBall() {

        Circle c2 = new Circle();
        c2.setCenterX(balls.get(0).getCentreX() + 30);
        c2.setCenterY(balls.get(0).getCentreY() + 30);
        c2.setRadius(10);
        c2.setFill(Color.BLUE);
        Ball b2 = new Ball(c2);
        //b2.setSpeed(balls.get(0).getSpeed());
        balls.add(b2);
        root.getChildren().add(b2.getHitBox());
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
        this.root.getChildren().remove(bonus.getHitBox());
    }



    public void lengthenPlatform() {
        if (platform.getW() <= 150 ) {
            if(platform.getX() + platform.getW() + 50 >= 550){
                platform.setX(platform.getX() - 50);
            }
            platform.setW(platform.getW() + 50);
        }
    }

    public void shortenPlatform() {
        if (platform.getW() >= 150 ) {
            platform.setW(platform.getW() - 50);
        }
    }


    public void setActualBrickNumber(int x) {
        this.actualBrickNumber = x;
    }

    public void setLaserShots(int x) {
        laserShots = x;
    }

    public void shootLaser(KeyEvent e) {

        /*if (laserShots == 3) {
            laserShots = 0;
            //Controller.laser = false;
        }*/
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
        this.playCanvas.getGraphicsContext2D().clearRect(0,0,550, 640);

        this.platform.render(gc);

        /*for(Ball b : balls) {
            b.render(gc);
        }*/

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


}
