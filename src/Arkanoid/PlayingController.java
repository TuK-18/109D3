package Arkanoid;

import javafx.animation.Timeline;
import javafx.stage.Stage;

public class PlayingController {

    Stage stage;
    Controller controller;
    //private AnchorPane anchorPane;
    //private Arkanoid.View view;
    Timeline timeline = new Timeline();

    //private Button tButton = new Button();

    public PlayingController(Controller controller) {

        this.stage = controller.getStage();
        this.controller = controller;
        //stage.getScene().
        //init();
        timeline.playFromStart();
    }

    public void updateState() {
        switch (Controller.curGameState) {
            case PLAYING:
                //System.out.println("playing");
                controller.animate();
                //testAnimate();
                break;
            case PAUSE:
                //System.out.println("pause");
                timeline.pause();
                controller.writeData();
                break;

            case LOSE:
                //System.out.println("lose");
                SoundManager.playLoseSfx();
                timeline.pause();
                //controller.stopMainTimer();
                //controller.showLoseScene();
                controller.showLoseScene();
                controller.resetPlayerData();
                //controller.reset();
                //controller.deepClean();
                //controller.writeData();
                break;

            case PRE_PLAYING:
                //System.out.println("pre");
                controller.setPlayScene();
                controller.animate();
                break;
            case LEVEL_UP:

                controller.stopMainTimer();
                controller.levelUp();
                controller.startMainTimer();
                break;
            case RESET:
                controller.stopMainTimer();
                SoundManager.stopLoseSfx();
                controller.reset();
                controller.startMainTimer();
                break;
            case WIN:
                timeline.pause();
                //controller.stopMainTimer();
                //controller.showLoseScene();
                controller.showWinScene();
                controller.resetPlayerData();
                break;
            case MENU:
                controller.showMenuScene();
                //System.out.println("menu");
                break;

        }
    }

}