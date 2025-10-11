

import javafx.event.EventHandler;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.*;
import org.w3c.dom.css.Rect;

import static java.awt.event.KeyEvent.KEY_PRESSED;

public class View {
    //private Data data;
    private Scene scene;
    private Ball b1;
    private Ball b2;
    private Ball b3;
    private Ball b4;
    private Ball b5;
    private Ball b6;
    private Platform platform;

    private final int WIDTH = 600;
    private final int HEIGHT = 640;
    private final int BALL_SIZE = 10;
    private ArrayList<GameObject>objects;



    public View() {
        //this.data = d;
        this.objects = new ArrayList<GameObject>();
        initialize();

    }

    public void initialize() {
        Group root = new Group();
        Circle onlyBall = new Circle();
        onlyBall.setCenterX(500);
        onlyBall.setCenterY(500);
        onlyBall.setRadius(20);
        onlyBall.setFill(Color.YELLOW);

        Circle circle2 = new Circle(600,600,20,Color.BLACK);

        Rectangle rect = new Rectangle();
        rect.setX(100);
        rect.setY(600);
        rect.setWidth(100);
        rect.setHeight(30);
        rect.setFill(Color.GREEN);

        this.platform = new Platform(rect);
        /*this.b2 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b3 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b4 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b5 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);
        this.b6 = new Ball(BALL_SIZE, WIDTH,HEIGHT, Color.DARKBLUE, Color.WHITESMOKE);*/
        this.b1 = new Ball(onlyBall);
        this.b2 = new Ball(circle2);
        this.objects.add(b1);
        this.objects.add(b2);
        this.objects.add(platform);
        /*this.balls.add(b2);
        this.balls.add(b3);
        this.balls.add(b4);
        this.balls.add(b5);
        this.balls.add(b6);*/
        root.getChildren().add(b1.getHitBox());
        root.getChildren().add(b2.getHitBox());
        root.getChildren().add(platform.getHitBox());
        this.scene = new Scene(root, WIDTH, HEIGHT);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                platform.handleEvent(event);
            }

        });

        /*scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                platform.handleEvent(event);
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

    public ArrayList<GameObject>getBalls() {
        return objects;
    }

}