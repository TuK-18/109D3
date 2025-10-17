import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


//nothing, only a container and buttons

public class PlayingSceneController {

    @FXML
    private Button pauseButton;

    @FXML
    private Button loseButton;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox playBox;

    private Stage stage;

    private View curView;

    private Controller controller;

    private Scene menuScene;

    private Timeline timeline = new Timeline();

    public PlayingSceneController(){}

    public PlayingSceneController(Controller controller) {
        this.controller = controller;
        //this.curView = controller.getView();
        this.stage = controller.getStage();
        pauseButton = new Button();
        loseButton = new Button();
        playBox = new VBox();



        initialize();
        timeline.playFromStart();
    }



    /*public void setController(Controller controller) {
        this.controller = controller;
        this.stage = controller.getStage();
    }*/


    public void initialize() {
        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.PAUSE;
                    System.out.println("you pressed pause");
                } else if (Controller.curGameState == Controller.GameState.PAUSE) {
                    System.out.println("now playing game");
                    Controller.curGameState = Controller.GameState.PLAYING;
                }
            }
        });


        loseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.LOSE;
                    //stage.setScene(menuScene);
                    System.out.println("lffkfkf");
                }
            }
        });

        Canvas canvas = new Canvas(400,300);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(10, 10, 100, 100);
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.strokeOval(200, 100, 80, 80);

        playBox.getChildren().add(canvas);



    }

    public void updateGameState() {
        switch (Controller.curGameState) {
            case PLAYING:
                //update the game logic
                //System.out.println("playing game");
                break;

            case PAUSE:
                //pause the timeline

                //System.out.println("game paused");
                timeline.pause();
                break;

            case LOSE:
                //System.out.println("loser");

                stage.setScene(menuScene);
                Controller.curGameState = Controller.GameState.MENU;
                //pause the timeline
                timeline.pause();
                break;

        }
    }

    public void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }

    public Controller getController() {
        return this.controller;
    }

}