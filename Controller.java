
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.animation.AnimationTimer;
import javafx.stage.*;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import java.util.Random;


public class Controller {
    private Stage stage;
    private View view;
    private CollisionManager collisionManager;

    private AnimationTimer gameLoop = null;

    private Scene loseScene;

    private Scene winScene;

    private Group tmpRoot;

    private Button playButton;



    public enum GameState{
        PRE_PLAYING,
        PLAYING,
        PAUSE,
        LOSE,
        MENU,
        LEVEL_UP,
        WIN
    }

    public static GameState curGameState = GameState.PRE_PLAYING;

    public Controller(Stage stage_){
        this.stage = stage_;
        this.stage.setWidth(800);
        view = new View();
        tmpRoot = new Group();
        playButton = new Button();
        playButton.setText("Play again ?");
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(Controller.curGameState == Controller.GameState.LOSE) {
                    Controller.curGameState = GameState.PRE_PLAYING;
                    reset();
                    //view = new View();
                    view.show(stage, view.getScene());
                    mainTimer.start();
                    //System.out.println("kkkkkkk");
                    //controller.getView().show(stage, controller.getView().getScene());
                }
            }
        });


        tmpRoot.getChildren().add(playButton);
        loseScene = new Scene(tmpRoot,400,400);
    }



    public Stage getStage() {
        return this.stage;
    }


    private PlayingController playingController;

    AnimationTimer mainTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            update();
            render();
        }
    };

    public void stopMainTimer() {
        mainTimer.stop();
    }

    public void startMainTimer() {
        mainTimer.start();
    }


    public void animate() {

        for (int j = 0; j < view.getBalls().size(); j++) {

            if (view.getBalls().get(j).getCentreY() >= 640) {
                view.removeBall(view.getBalls().get(j));
                //j--;
                continue;
            }

            if (view.getBalls().get(j) != null) {
                view.getBalls().get(j).move();
            } //else {
            //b.move();
            //}
            //b.move();
            //auxAnimate(b);
            view.getBalls().get(j).detectCollision(view.getPlatform());


            for (int i = 0; i < view.getBricks().size(); i++) {
                if (view.getBalls().get(j).detectCollision(view.getBricks().get(i))) {


                    view.getBricks().get(i).reduceDensity();

                    if (view.getBricks().get(i).getDensity() <= 0
                            && view.getBricks().get(i).isBreakable()) {

                        view.spawnBonus(view.getBricks().get(i).getX()
                                        , view.getBricks().get(i).getY(), 1);

                        view.removeFromWorld(view.getBricks().get(i));
                                
                        i--;
                        //continue;

                    }
                }
            }
            // bonus
            if (!view.getBonuses().isEmpty()) {
                for (int i = 0; i < view.getBonuses().size(); i++) {
                    view.getBonuses().get(i).move();
                    if (view.getBonuses().get(i).detectCollision(view.getPlatform())) {

                        handleBonus(view.getBonuses().get(i).getType());
                        view.removeBonus(view.getBonuses().get(i));
                        continue;

                    }

                    if (view.getBonuses().get(i).getX() >= 640) {
                        view.removeBonus(view.getBonuses().get(i));
                    }
                }
            }


            view.getPlatform().move();

        }
    }
        private void handleBonus(int type) {
            switch (type) {
                case 1:
                    //ADD BALL
                    this.view.addBall();
                    break;
                case 2:
                    //ADD 100 POINTS
//                    this.curPoint += 100;
//                    break;
                case 3:
                    //MAKE PLATFORM STICKY
//                    this.isSticky = true;
                    //curGameState = GameState.PRE_PLAYING;
                    break;
                case 4:
//                    this.curPoint -= 500;
                    break;
                case 5:
                    view.setActualBrickNumber(0);
                    break;
                case 6:
                    view.modifyBallSpeed(1.5);

                    break;
                case 7:
                    view.lengthenPlatform();
                    break;

                case 8:
                    view.shortenPlatform();
                    break;
                case 9:
                    curGameState = GameState.LOSE;
                    break;
                case 10:
                    //+1 LIVE
//                    this.curLives += 1;
                    break;
                case 11:
                    //SLOW BALLS
                    view.modifyBallSpeed(0.5);
                    break;


            }
        }

    private int RNG(int low, int high) {
        Random random = new Random();
        return random.nextInt(high + 1 - low) + low;
    }

    public void update() {
        playingController.updateState();
    }

    public void render() {
        this.view.render();
    }

    public void showLoseScene() {
        //this.view.showLose(stage);

        stage.setScene(loseScene);
        stage.show();
    }

    public void run() {
        playingController = new PlayingController(this);
        if(curGameState == GameState.PLAYING || curGameState == GameState.PRE_PLAYING) {
            this.view.show(stage, this.view.getScene());
        } else if (curGameState == GameState.LOSE) {
            //this.view.showLose(stage);
            this.showLoseScene();
        }
        mainTimer.start();
    }

    public void reset() {
        view = new View();

    }

    private void auxAnimate(GameObject ball) {
        for (Ball b : this.view.getBalls()) {
            ball.detectCollision(b);
        }
    }

    private void axuAnimate(Platform platform) {
        for (Ball b : this.view.getBalls()) {
            b.detectCollision(platform);
        }
    }
}