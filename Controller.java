
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.text.View;

import javafx.animation.*;
import javafx.animation.AnimationTimer;
import javafx.stage.*;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.text.View;


public class Controller {
    private Stage stage;
    private View view;
    private CollisionManager collisionManager;

    private AnimationTimer gameLoop = null;

    private Scene loseScene;

    private Scene winScene;

    private LoseSceneController loseSceneController;
    private WinSceneController winSceneController;
    private MenuSceneController menuSceneController;

    private Group tmpRoot;

    private Button playButton;

    private int curPoint = 0;
    private int curLevel = 1;
    private int curLives = 10;
    //TODO : ADD HIGHSCORE
    private int highScore ;

    private final int DEFAULT_POINT = 0;
    private final int DEFAULT_LEVEL = 1;
    private final int DEFAULT_LIVES = 10;
    private final int TOTAL_LEVELS = 7;

    private boolean isSticky = false;
    public static boolean laser = false;
    private boolean magnet = false;
    private int ballPower = 1;


    public enum GameState{
        PRE_PLAYING,
        PLAYING,
        PAUSE,
        LOSE,
        MENU,
        LEVEL_UP,
        WIN
    }

    public static GameState curGameState = GameState.MENU;

    public Controller(Stage stage_){
        this.stage = stage_;
        this.stage.setWidth(800);
        view = new View(curLevel);
        view.setCurScoreText(curPoint);
    }



    public Stage getStage() {
        return this.stage;
    }

    public void setCurPoint(int x) {
        curPoint = x;
    }
    
    public void setCurLevel(int x) {
        curLevel = x;
    }

    public void setCurLives(int x) {
        curLives = x;
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
            }

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
        }
            // bonus
        if (!view.getBonuses().isEmpty()) {
            for (int i = 0; i < view.getBonuses().size(); i++) {

                if (magnet) {
                    view.getBonuses().get(i).move(view.getPlatform().getX()
                                    + view.getPlatform().getW() / 2
                            , view.getPlatform().getY());

                } else {
                    view.getBonuses().get(i).move();
                }

                if (view.getBonuses().get(i).detectCollision(view.getPlatform())) {

                    if (Controller.curGameState == GameState.PLAYING) {
                        handleBonus(view.getBonuses().get(i).getType());
                    }
                    view.removeBonus(view.getBonuses().get(i));
                    continue;

                }

                if(view.getBonuses().get(i).getX() >= 640) {
                    view.removeBonus(view.getBonuses().get(i));
                }
            }
        }


        view.getPlatform().move();

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

                if(curLevel > 2) {
                    curGameState = GameState.WIN;
                } else {
                    curGameState = GameState.LEVEL_UP;
                    //view = new View(curLevel);
                }
            }
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
                    this.curPoint += 100;
                    break;
                case 3:
                    //MAKE PLATFORM STICKY
                    //this.isSticky = true;
                    //curGameState = GameState.PRE_PLAYING;
                    break;
                case 4:
                    this.curPoint -= 500;
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
                    this.curLives += 1;
                    break;
                case 11:
                    //SLOW BALLS
                    view.modifyBallSpeed(0.5);
                    break;
                case 12:
                    //LOAD THE LASER
                    //laser = true;
                    //view.shootLaser(laser);
                    view.setLaserShots(3);
                    break;
                case 13:
                    //BONUSES FLY TOWARDS PLATFORM
                    magnet = true;
                    break;

                case 14:
                    //MAKE BALL STRONGER
                    this.ballPower = 2;
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

    //clean all unneeded map files
    public void deepClean() {
        File tmpFile = new File("res/map0.data");
        for (int i = 1 ; i <= TOTAL_LEVELS ; i++) {

            tmpFile = new File("res/map" + i + ".data");
            if (tmpFile.delete()) {
                System.out.println("deleted " + i);
            }

        }
    }

    //clean the map file of the next level
    public void cleanOne(int i) {
        //File tmpFile = new File("res/map0.data");
        File tmpFile = new File("res/map"+i+".data");
        if (tmpFile.delete()) {
            System.out.println("deleted " + i);
        }
    }

    public void showLoseScene() {
        //this.view.showLose(stage);

        stage.setScene(loseScene);
        stage.show();
    }

    public void run() {
        playingController = new PlayingController(this);

        winSceneController = new WinSceneController();
        loseSceneController = new LoseSceneController();
        menuSceneController = new MenuSceneController();

        if(curGameState == GameState.PLAYING || curGameState == GameState.PRE_PLAYING) {
            this.view.show(stage, this.view.getScene());
            this.view.setCurScoreText(curPoint);
            this.view.setCurLivesText(curLives);
            this.view.setHighScoreText(highScore);
        } else if (curGameState == GameState.MENU) {
            this.showMenuScene();
            stage.show();
        }
        mainTimer.start();
    }

    //RESET EVERYTHING AND CLEAR ALL MAP DATA
    public void reset() {
        deepClean();
        resetBonus();
        view = new View(1);
        view.setCurScoreText(0);
        view.setCurLivesText(10);
        view.show(stage, view.getScene());
        curLevel = 1;
        curPoint = 0;
        curLives = 10;
        //deepClean();
        writeData();
        curGameState = GameState.PRE_PLAYING;
    }

    public void resetPlayerData() {
        deepClean();
        curLevel = 1;
        //curPoint = 0;
        //CAN'T RESET POINT YET BECAUSE
        //THE LOSE & WIN SCENE NEED TO SHOW IT
        curLives = 10;
        writeData();
    }

    public void resetBonus() {
        this.isSticky = false;
        laser = false;
        magnet = false;
        this.ballPower = 1;
        view.setLaserShots(0);
        view.getPlatform().setW(150);
    }

    public void levelUp() {
        //curGameState = GameState.PRE_PLAYING;
        //deepClean();
        cleanOne(curLevel);
        view = new View(curLevel);
        view.setCurScoreText(curPoint);
        view.setCurLivesText(curLives);
        view.setHighScoreText(highScore);
        view.show(stage,view.getScene());
        //deepClean();
        resetBonus();
        writeData();
        curGameState = GameState.PRE_PLAYING;
    }

    /**
     * SET CURRENT SCORE AND HIGH SCORE FOR THE
     * LOSE SCENE TO SHOW
     * */

    public void otherLoseScene() {
        loseSceneController.setHighScore(highScore);
        loseSceneController.setLastScore(curPoint);
        loseSceneController.showScene(stage);
    }

    /**
     * SET CURRENT SCORE AND HIGH SCORE FOR THE
     * WIN SCENE TO SHOW.
     * */

    public void showWinScene() {
        winSceneController.setHighScore(highScore);
        winSceneController.setLastScore(curPoint);
        winSceneController.showScene(stage);
    }

    public void showMenuScene() {
        menuSceneController.showScene(stage);
    }

    public void setPlayScene() {
        stage.setScene(view.getScene());
    }

    /**
     * READ POINT, LEVEL, AND LIVES FROM A FILE
     * READ HIGH SCORE FROM A DIFFERENT FILE.*/

    public void readData() {
        //point
        //level
        //lives
        try {
            File file = new File("res/playerData.txt");

            FileReader reader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(reader);
            String point = bufferedReader.readLine();
            curPoint = Integer.parseInt(point);
            String level = bufferedReader.readLine();
            curLevel = Integer.parseInt(level);
            String lives = bufferedReader.readLine();
            curLives = Integer.parseInt(lives);

            if (curLevel > TOTAL_LEVELS) {
                curPoint = DEFAULT_POINT;
                curLevel = DEFAULT_LEVEL;
                curLives = DEFAULT_LIVES;
            }

        } catch (FileNotFoundException e) {
            curPoint = DEFAULT_POINT;
            curLevel = DEFAULT_LEVEL;
            curLives = DEFAULT_LIVES;
            System.out.println("Can't find res/playerData.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File highScoreFile = new File("res/highScore.txt");

            FileReader reader = new FileReader(highScoreFile);

            BufferedReader bufferedReader = new BufferedReader(reader);
            String tmpHighScore = bufferedReader.readLine();
            //highSc =
            highScore = Integer.parseInt(tmpHighScore);


        } catch (FileNotFoundException e) {
            System.out.println("Can't find res/highScore.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
    
}
