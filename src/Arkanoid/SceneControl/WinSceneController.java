package Arkanoid.SceneControl;

import Arkanoid.Controller;
import Arkanoid.SoundManager;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class WinSceneController extends SceneController {

    public WinSceneController() {
        super();
        //this.lastScore = x;
        this.scene = new Scene(root,800,500);
        initialize();

    }

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
                //System.out.println("lllllllllllllll");
                if(Controller.curGameState == Controller.GameState.WIN) {
                    SoundManager.playClip2();
                    Controller.curGameState = Controller.GameState.RESET;
                    //if(controller != null)controller.startMainTimer();
                }
            }
        });

    }

}




