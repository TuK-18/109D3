
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

//TODO: ADD FONT



public class Controller {
    private Stage stage;
    private View view;
    private CollisionManager collisionManager;

    private AnimationTimer gameLoop = null;

    private Scene loseScene;

    private Scene winScene;

    private Group tmpRoot;

    private Button playButton;

    private Text lastScoreText;

    private int curPoint = 0;
    private int curLevel = 4;
    private int curLives = 10;

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
        view = new View(curLevel);
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
        lastScoreText = new Text();
        lastScoreText.setX(200);
        lastScoreText.setY(200);
        tmpRoot.getChildren().add(lastScoreText);
        tmpRoot.getChildren().add(playButton);
        loseScene = new Scene(tmpRoot,400,400);
    }



    public Stage getStage() {
        return this.stage;
    }

    public void show(){
        this.view.show(stage, this.view.getScene());
        animate();
        gameLoop.start();

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
        /*
        KeyFrame k = new KeyFrame(Duration.millis(10), e ->
        {
            for (GameObject b : this.view.getBalls() ) {
                if(b instanceof Ball) {
                    b.move();
                } //else {
                    //b.move();
                //}
                //b.move();
                //auxAnimate(b);
                b.detectCollision(view.getPlatform());

                for (int i = 0; i < view.getBricks().size(); i++) {
                    if (b.detectCollision(view.getBricks().get(i))) {


                        view.getBricks().get(i).reduceDensity();
                        if (view.getBricks().get(i).getDensity() <= 0) {
                            view.removeFromWorld(view.getBricks().get(i));
                            i--;

                        }
                    }
                }

            }

            this.view.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    view.getPlatform().handleEvent(event);
                    //platform.move();
                }
            });

            this.view.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    view.getPlatform().handleEvent(event);
                    view.spawnBall(event);
                    //platform.move();
                }
            });

            this.view.getPlatform().move();

        });

        Timeline t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
        */

        /*gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {*/
                for (int j = 0; j < view.getBalls().size(); j++) {

                    if(view.getBalls().get(j).getCentreY() >= 640) {
                        view.removeBall(view.getBalls().get(j));
                        //j--;
                        continue;
                    }

                    if(view.getBalls().get(j) != null) {
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
                            if(view.getBricks().get(i).isBreakable())curPoint+=10;
                            if (view.getBricks().get(i).getDensity() <= 0
                                    && view.getBricks().get(i).isBreakable()) {



                                view.spawnBonus(view.getBricks().get(i).getX()
                                        , view.getBricks().get(i).getY(), 1);

                                view.removeFromWorld(view.getBricks().get(i));
                                /*view.spawnBonus(view.getBricks().get(i).getX()
                                        , view.getBricks().get(i).getY(), 1);*/
                                i--;
                                //continue;

                            }
                        }
                    }

                }
                if (!view.getBonuses().isEmpty()) {
                    for (int i = 0; i < view.getBonuses().size(); i++) {
                        view.getBonuses().get(i).move();
                        if (view.getBonuses().get(i).detectCollision(view.getPlatform())) {
                            handleBonus(view.getBonuses().get(i).getType());
                            view.removeBonus(view.getBonuses().get(i));
                            continue;
                        }

                        if(view.getBonuses().get(i).getX() >= 640) {
                            view.removeBonus(view.getBonuses().get(i));
                        }
                    }
                }

                view.setCurScoreText(curPoint);
                view.getPlatform().move();


            //}
        //};



        if(curGameState == GameState.PLAYING) {
            if (view.getBalls().isEmpty()) {
                curGameState = GameState.PRE_PLAYING;
                curLives-=1;
                if (curLives <= 0) {
                    curGameState = GameState.LOSE;
                }
            }

            if (view.getActualBrickNumber() == 0) {
                curLevel+=1;

                if(curLevel > 4) {
                    curGameState = GameState.LOSE;
                } else {
                    curGameState = GameState.LEVEL_UP;
                    //view = new View(curLevel);
                }
            }
        }


    }

    public void update() {
        playingController.updateState();
    }


    public void render() {
        this.view.render();
    }

    public void showLoseScene() {
        //this.view.showLose(stage);
        lastScoreText.setText("YOUR SCORE: " + curPoint);
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
        view = new View(1);
        curLevel = 1;
        curPoint = 0;
        curLives = 10;
    }

    /*public int generateRandomNum(int start, int end) {

    }*/

    /**
     * if platform width is 50 do not shrink
     * if platform width is 200 do not expand
     * if balls.size() > 10 do not add more
     **/

    public void handleBonus(int type) {
        switch (type) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }


    public void levelUp() {
        //curGameState = GameState.PRE_PLAYING;
        view = new View(curLevel);
        view.show(stage,view.getScene());
        curGameState = GameState.PRE_PLAYING;
    }




    //đừng để ý mấy cái này
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