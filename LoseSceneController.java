
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


import java.io.IOException;
import java.util.Objects;

public class LoseSceneController extends SceneController{

    private FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000),root);

    public LoseSceneController() {
        super();
        //this.lastScore = x;
        this.scene = new Scene(root,800,500);

        root.setOpacity(0.0);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        //fadeTransition.play();
        initialize();

    }

    public void initialize() {

        sceneText.setText("YOU LOST");
        sceneText.setLayoutX(300);
        sceneText.setLayoutY(100);

        lastScoreText.setText("YOUR SCORE \n" + lastScore);
        lastScoreText.setLayoutX(250);
        lastScoreText.setLayoutY(300);

        highScoreText.setText("HIGH SCORE \n" + highScore);
        highScoreText.setLayoutX(450);
        highScoreText.setLayoutY(300);

        playAgainButton.setText("PLAY AGAIN");
        playAgainButton.setLayoutX(270);
        playAgainButton.setLayoutY(190);

        //this.lastScoreTxt = new Text();
        //lastScoreTxt.setText("YOUR POINT\n" + lastScore);
        playAgainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("lllllllllllllll");
                if(Controller.curGameState == Controller.GameState.LOSE) {
                    SoundManager.playClip2();
                    Controller.curGameState = Controller.GameState.RESET;
                    //if(controller != null)controller.startMainTimer();
                }
            }
        });
    }

    public void fadesIn() {
        fadeTransition.play();
    }

}




