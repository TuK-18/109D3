
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.Objects;

public class PlayingController {


    Stage stage;
    Controller controller;
    //private AnchorPane anchorPane;
    //private View view;
    Timeline timeline = new Timeline();



    //private Button tButton = new Button();

    public PlayingController(Controller controller) {
        //anchorPane = this.anchorPane =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("testButton.fxml")));

        this.stage = controller.getStage();
        this.controller = controller;
        //stage.getScene().
        //init();
        timeline.playFromStart();
    }

    /*public void init() {
        controller.getView().gettButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Controller.curGameState == Controller.GameState.PLAYING) {
                    Controller.curGameState = Controller.GameState.PAUSE;
                } else if (Controller.curGameState == Controller.GameState.PAUSE) {
                    Controller.curGameState = Controller.GameState.PLAYING;
                }
            }
        });
        //controller.getView().getScene().getRoot().getChildren()
    }*/

    //public void setController() {}


    public void updateState() {
        switch (Controller.curGameState) {
            case PLAYING:
                System.out.println("playing");
                controller.animate();
                //testAnimate();
                break;
            case PAUSE:
                System.out.println("pause");
                timeline.pause();
                break;
            /*case LOSE:
                timeline.pause();

                break;*/
            case LOSE:
                System.out.println("lose");

                timeline.pause();
                controller.stopMainTimer();
                controller.showLoseScene();

                break;

            case PRE_PLAYING:
                System.out.println("pre playing");
                controller.animate();
                break;

        }
    }



    /*
    public void testAnimate() {
        for (Ball b : view.getBalls() ) {
            b.move();
            axuAnimate(b);
        }
    }

    private void axuAnimate(Ball ball) {
        for (Ball b : this.view.getBalls()) {
            ball.detectCollision(b);
        }
    }*/
}