
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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

public class WinSceneController extends SceneController{

    /*public WinSceneController(){

    }*/




    public WinSceneController() {
        super();
        //this.lastScore = x;
        this.scene = new Scene(root,800,500);
        initialize();

    }


    //initialize();
    public void initialize() {

        sceneText.setText("YOU WON");
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
                
            }
        });


    }


}




