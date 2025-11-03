package Arkanoid;

import Arkanoid.GameObjects.Bullet;
import Arkanoid.GameObjects.PVector;
import Arkanoid.SceneControl.LoseSceneController;
import Arkanoid.SceneControl.MenuSceneController;
import Arkanoid.SceneControl.WinSceneController;
import javafx.animation.AnimationTimer;
import javafx.stage.*;

import java.io.*;
import java.util.Objects;
import java.util.Random;

//TODO: ADD FONT
//TODO: ADD SOUND

public class Controller {
    private Stage stage;
    private View view;
    private CollisionManager collisionManager = new CollisionManager();

    private LoseSceneController loseSceneController;
    private WinSceneController winSceneController;
    private MenuSceneController menuSceneController;

    private int curPoint = 0;
    private int curLevel = 1;
    private int curLives = 10;

    private final int DEFAULT_POINT = 0;
    private final int DEFAULT_LEVEL = 1;
    private final int DEFAULT_LIVES = 10;
    private final int TOTAL_LEVELS = 9;

    private int highScore;
    private boolean isSticky = false;
    public static boolean laser = false;
    private boolean magnet = false;
    private int ballPower = 1;
    //private int laserShots = 0;

    private SoundManager soundManager;

    public enum GameState{
        PRE_PLAYING,
        PLAYING,
        PAUSE,
        LOSE,
        RESET,
        MENU,
        LEVEL_UP,
        WIN
    }

    public static GameState curGameState = GameState.MENU;

    private static Controller instance;

    private Controller(Stage stage_) {
        soundManager = SoundManager.getSoundManagerInstance();
        readData();
        this.stage = stage_;
        this.stage.setWidth(800);
        //InputStream fontStream = getClass().getResourceAsStream("/res/fontName.ttf");

        view = new View(curLevel);
        view.setCurScoreText(curPoint);
        view.setHighScoreText(highScore);
    }

    public static Controller getInstance(Stage stage) {
        if (instance == null) {
            instance = new Controller(stage);
        }
        return instance;
    }

    public Stage getStage() {
        return this.stage;
    }

    public View getView() {
        return this.view;
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

    //MOVES OBJECTS AROUND
    //HANDLE EVENTS
    public void animate() {

        for (int j = 0; j < view.getBalls().size(); j++) {

            if(view.getBalls().get(j).getCentreY() >= 640
                    || view.getBalls().get(j).getCentreY() < -10) {
                view.removeBall(view.getBalls().get(j));
                //j--;
                continue;
            }

            if(view.getBalls().get(j) != null) {
                view.getBalls().get(j).move();
            }


            if ( view.getPlatform().handleObjectCollision(view.getBalls().get(j)) ) {
                if (view.getSticky()) {
                    //System.out.println("stick");
                    view.getBalls().get(j).setSpeed(new PVector(0,0));
                }
            }

            if(Objects.equals(view.getBalls().get(j).getSpeed(), new PVector(0, 0))){
                if (view.getPlatform().getX() + view.getPlatform().getW() < 550
                        && view.getPlatform().getX() > 0) {
                    view.getBalls().get(j).setCentreX(

                            view.getBalls().get(j).getCentreX()
                                    + view.getPlatform().getxVelocity());

                }
            }
                    /*if (isSticky) {
                        view.getBalls().get(j).setCentreX(view.getPlatform().getX());
                    }*/
            for (int i = 0; i < view.getBricks().size(); i++) {

                if (view.getBalls().get(j) instanceof Bullet) {
                    if (((Bullet) view.getBalls().get(j))
                            .detectCollision(view.getBricks().get(i))) {
                        SoundManager.playExplosionClip();
                        view.getBricks().get(i).reduceDensity(ballPower);
                        //if(view.getBricks().get(i).isBreakable())curPoint+=10;
                        if (view.getBricks().get(i).getDensity() <= 0
                                && view.getBricks().get(i).isBreakable()) {

                            curPoint += (10) * view.getBricks().get(i).getType();

                            if (RNG(0, 0) == 0) {
                                SoundManager.playExplosionClip();
                                view.spawnBonus(view.getBricks().get(i).getX()
                                        , view.getBricks().get(i).getY(), RNG(1, 14));
                            }
                            view.removeFromWorld(view.getBricks().get(i));

                            i--;
                        }
                    }
                } else {
                    if (view.getBalls().get(j).handleObjectCollision((view.getBricks().get(i)))) {

                        view.getBricks().get(i).reduceDensity(view.getBallPower());
                        //if(view.getBricks().get(i).isBreakable())curPoint+=10;
                        if (view.getBricks().get(i).getDensity() <= 0
                                && view.getBricks().get(i).isBreakable()) {

                            curPoint += (10) * view.getBricks().get(i).getType();

                            if (RNG(0, 0) == 0) {
                                SoundManager.playExplosionClip();
                                view.spawnBonus(view.getBricks().get(i).getX()
                                        , view.getBricks().get(i).getY(), RNG(1, 14));
                            }
                            view.removeFromWorld(view.getBricks().get(i));
                            i--;

                        }
                    }
                }
            }
        }

        if (!view.getBonuses().isEmpty()) {
            for (int i = 0; i < view.getBonuses().size(); i++) {

                if (view.getMagnet()) {
                    view.getBonuses().get(i).move(view.getPlatform().getX()
                                    + view.getPlatform().getW() / 2
                            , view.getPlatform().getY());

                } else {
                    view.getBonuses().get(i).move();
                }

                // check va cham giua bonus va platform
                if (collisionManager.detectBoxBoxCollision(view.getBonuses().get(i)
                        , view.getPlatform())) {

                    // su ly bonus
                    if (Controller.curGameState == GameState.PLAYING) {
                        //handleBonus(view.getBonuses().get(i).getType());
                        switch (view.getBonuses().get(i).getType()) {
                            case 2:
                                this.curPoint += 100;
                                break;
                            case 4:
                                this.curPoint -= 500;
                                break;
                            case 9:
                                //INSTA DEATH
                                loseSceneController.fadesIn();
                                curGameState = Controller.GameState.LOSE;
                                break;
                            case 10:
                                //+1 LIVE
                                this.curLives += 1;
                                writeData();
                                break;
                            default:
                                view.getBonuses().get(i).handleBonus(view);
                                break;
                        }
                    }
                    view.removeBonus(view.getBonuses().get(i));
                    continue;
                }

                // ra khoi man hinh choi thi xoa
                if(view.getBonuses().get(i).getX() >= 640) {
                    view.removeBonus(view.getBonuses().get(i));
                }
            }
        }

        view.setHighScoreText(highScore);
        view.setCurScoreText(curPoint);
        view.setCurLivesText(curLives);
        view.getPlatform().move();


        //}
        //};

        if (curPoint >= highScore) {
            highScore = curPoint;
        }

        if(curGameState == GameState.PLAYING) {
            //System.out.println(view.getActualBallNumber());
            //System.out.println(view.getPlatform().getX());
            if (view.getActualBallNumber() <= 0) {
                //reset some bonus here
                this.resetBonus();
                curGameState = GameState.PRE_PLAYING;
                curLives-=1;
                SoundManager.playLoseLifeClip();
            }
        }

        if (curLives <= 0) {
            loseSceneController.fadesIn();
            curGameState = GameState.LOSE;
            return;
        }

        if (view.getActualBrickNumber() == 0) {
            curLevel+=1;
            //reset all bonus here;
            this.resetBonus();
            if(curLevel > TOTAL_LEVELS) {
                curGameState = GameState.WIN;
            } else {
                curGameState = GameState.LEVEL_UP;
                //view = new Arkanoid.View(curLevel);
            }
        }
    }


    public void update() {
        playingController.updateState();
    }

    //SHOW THINGS ON THE SCREEN
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

    //START THE GAME
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
            //this.view.showLose(stage);
            //this.showLoseScene();
            //this.otherLoseScene();
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
        curLevel = DEFAULT_LEVEL;
        curPoint = DEFAULT_POINT;
        curLives = DEFAULT_LIVES;
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

        view.setSticky(false);
        view.setMagnet(false);
        view.setBallPower(1);
        /*this.isSticky = false;
        laser = false;
        magnet = false;
        this.ballPower = 1;*/
        //this.laserShots = 0;
        view.setLaserShots(0);
        //view.getPlatform().setW(150);
        if(view.getPlatform().getX() + 150 >= 550){
            view.getPlatform().setX(view.getPlatform().getX() - 150);
        }
        view.getPlatform().setW(150);
    }

    /**
     * if platform width is 50 do not shrink
     * if platform width is 200 do not expand
     * if balls.size() == 10 do not add more.
     **/

    /*public void handleBonus(int type) {
        switch (type) {
            case 1:
                //ADD BALL
                if (!view.getBalls().isEmpty()) {
                    this.view.addBall();
                }
                break;
            case 2:
                //ADD 100 POINTS
                this.curPoint += 100;
                break;
            case 3:
                //MAKE PLATFORM STICKY
                this.isSticky = true;

                if (this.isSticky) {
                    this.isSticky = false;
                } else {
                    this.isSticky = true;
                }
                break;
            case 4:

                this.curPoint -= 500;
                break;
            case 5:
                //FAST FORWARD TO THE NEXT LEVEL
                //curLevel+=1;
                //levelUp();
                view.setActualBrickNumber(0);
                break;
            case 6:
                //FAST BALLS
                view.modifyBallSpeed(1.5);
                break;
            case 7:
                //LONG PLATFORM
                view.lengthenPlatform();
                break;
            case 8:
                //SHORT PLATFORM
                view.shortenPlatform();
                break;
            case 9:
                //INSTA DEATH
                loseSceneController.fadesIn();
                curGameState = GameState.LOSE;
                break;
            case 10:
                //+1 LIVE
                this.curLives += 1;
                writeData();
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
    }*/

    private int RNG(int low, int high) {
        Random random = new Random();
        return random.nextInt(high + 1 - low) + low;
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

    public void showLoseScene() {
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
            curPoint = DEFAULT_POINT;
            curLevel = DEFAULT_LEVEL;
            curLives = DEFAULT_LIVES;
            e.printStackTrace();
        }

        try {
            File highScoreFile = new File("res/highScore.txt");

            FileReader reader = new FileReader(highScoreFile);

            BufferedReader bufferedReader = new BufferedReader(reader);
            String tmpHighScore = bufferedReader.readLine();
            highScore = Integer.parseInt(tmpHighScore);

        } catch (FileNotFoundException e) {
            highScore = 0;
            System.out.println("Can't find res/highScore.txt");
        } catch (IOException e) {
            highScore = 0;
            e.printStackTrace();
        }

    }

    /**
     * WRITE POINT, LEVEL, AND LIVES TO A FILE
     * WRITE HIGH SCORE TO A DIFFERENT FILE.*/

    public void writeData() {
        try {
            File writeToThis = new File("res/playerData.txt");

            FileWriter writer = new FileWriter(writeToThis);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(curPoint + "\n");
            bufferedWriter.write(curLevel + "\n");
            bufferedWriter.write(curLives + "\n");

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File writeToThis = new File("res/highScore.txt");

            FileWriter writer = new FileWriter(writeToThis);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(highScore + "\n");

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}