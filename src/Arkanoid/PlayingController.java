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
                if(Arkanoid.Controller.curGameState == Arkanoid.Controller.GameState.PLAYING) {
                    Arkanoid.Controller.curGameState = Arkanoid.Controller.GameState.PAUSE;
                } else if (Arkanoid.Controller.curGameState == Arkanoid.Controller.GameState.PAUSE) {
                    Arkanoid.Controller.curGameState = Arkanoid.Controller.GameState.PLAYING;
                }
            }
        });
        //controller.getView().getScene().getRoot().getChildren()
    }*/

    //public void setController() {}


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
            /*case LOSE:
                timeline.pause();

                break;*/
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
                //controller.showMenuScene();
                //System.out.println("menu");
                break;
            /*case MENU:
                //this.sc
                Group tmpRoot = new Group();
                Button playButton = new Button();
                playButton.setText("play");
                playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(Arkanoid.Controller.curGameState == Arkanoid.Controller.GameState.MENU) {
                            Arkanoid.Controller.curGameState = Arkanoid.Controller.GameState.PLAYING;
                            controller.getView().show(stage, controller.getView().getScene());
                        }
                    }
                });
                tmpRoot.getChildren().add(playButton);
                Scene loseScene = new Scene(tmpRoot,400,400);
                stage.setScene(loseScene);
                break;*/
        }
    }



    /*
    public void testAnimate() {
        for (Arkanoid.GameObjects.Ball b : view.getBalls() ) {
            b.move();
            axuAnimate(b);
        }
    }

    private void axuAnimate(Arkanoid.GameObjects.Ball ball) {
        for (Arkanoid.GameObjects.Ball b : this.view.getBalls()) {
            ball.detectCollision(b);
        }
    }*/
}