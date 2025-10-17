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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuSceneController {
    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    @FXML
    private AnchorPane root;

    private Stage stage;

    private View curView;

    private Controller controller;

    private Scene playingScene;

    public MenuSceneController() {};

    public MenuSceneController(Controller controller) {
        this.controller = controller;
        //this.curView = controller.getView();
        this.stage = controller.getStage();
        playButton = new Button();
        quitButton = new Button();
        initialize();

    }

    public void initialize() {
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Controller.curGameState == Controller.GameState.MENU) {
                    Controller.curGameState = Controller.GameState.PLAYING;
                    System.out.println("from menu to play");
                    //stage.setScene(playingScene);
                } /*else if (controller.curGameState == Controller.GameState.PAUSE) {
                    controller.curGameState = Controller.GameState.PLAYING;
                }*/
            }
        });


        /*quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(controller.curGameState == Controller.GameState.MENU) {
                    //controller.curGameState = Controller.GameState.MENU;
                }
            }
        });*/
    }

    public void updateGameState() {
        switch (Controller.curGameState) {
            case PLAYING:
                //Controller.curGameState = Controller.GameState.PLAYING;
                stage.setScene(playingScene);
                break;
        }
    }


    public Controller getController() {
        return this.controller;
    }

    public void setPlayingScene(Scene playingScene) {
        this.playingScene = playingScene;
    }
}