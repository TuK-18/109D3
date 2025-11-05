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

        view.setCurScoreText(curPoint);
        view.setHighScoreText(highScore);
        view.setCurLivesText(curLives);
    }

    public static Controller getInstance(Stage stage) {
        if (instance == null) {
            instance = new Controller(stage);
        }
        return instance;
    }

    public static Controller getInstance() {
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
                //System.out.println("not null");
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

            for (int i = 0; i < view.getBricks().size(); i++) {

                if (view.getBalls().get(j) instanceof Bullet) {
                    if (((Bullet) view.getBalls().get(j))
                            .detectCollision(view.getBricks().get(i))) {

                        SoundManager.playExplosionClip();
                        view.getBricks().get(i).reduceDensity(view.getBallPower());

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
                if(view.getBonuses().get(i).getY()
                        + view.getBonuses().get(i).getH() > 640) {
                    view.removeBonus(view.getBonuses().get(i));
                }
            }
        }

        view.setHighScoreText(highScore);
        view.setCurScoreText(curPoint);
        view.setCurLivesText(curLives);
        view.getPlatform().move();
        view.setSaveThis(curGameState);

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
                view.setSaveThis(GameState.PRE_PLAYING);
                curLives-=1;
                SoundManager.playLoseLifeClip();
            }
        }

        if (curLives <= 0) {
            loseSceneController.fadesIn();
            curGameState = GameState.LOSE;
            view.setSaveThis(GameState.PRE_PLAYING);
            return;
        }

        if (view.getActualBrickNumber() == 0) {
            curLevel+=1;
            //reset all bonus here;
            this.resetBonus();
            if(curLevel > TOTAL_LEVELS) {
                curGameState = GameState.WIN;
                view.setSaveThis(GameState.PRE_PLAYING);
            } else {
                curGameState = GameState.LEVEL_UP;
                view.setSaveThis(GameState.PRE_PLAYING);
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


    //START THE GAME
    public void run() {
        playingController = new PlayingController(this);

        winSceneController = new WinSceneController();
        loseSceneController = new LoseSceneController();
        menuSceneController = new MenuSceneController();

        this.showMenuScene();
        stage.show();

        mainTimer.start();
    }

    //RESET EVERYTHING AND CLEAR ALL MAP DATA
    public void reset() {
        //deepClean();
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
        view.setSaveThis(GameState.PRE_PLAYING);
    }

    public void resetPlayerData() {
        //deepClean();
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



    private int RNG(int low, int high) {
        Random random = new Random();
        return random.nextInt(high + 1 - low) + low;
    }

    public void levelUp() {
        //curGameState = GameState.PRE_PLAYING;
        //deepClean();
        //cleanOne(curLevel);
        view = new View(curLevel);
        view.setCurScoreText(curPoint);
        view.setCurLivesText(curLives);
        view.setHighScoreText(highScore);
        view.show(stage,view.getScene());
        //deepClean();
        resetBonus();
        writeData();
        curGameState = GameState.PRE_PLAYING;
        view.setSaveThis(GameState.PRE_PLAYING);
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

        try {
            File file = new File("res/saved.data");

            FileInputStream fileInputStream = new FileInputStream(file);

            ObjectInputStream os = new ObjectInputStream(fileInputStream);

            view = (View) os.readObject();
            view.initialize(true);
        } catch (FileNotFoundException e) {
            curPoint = 0;
            curLevel = 1;
            curLives = 10;
            view = new View(curLevel);
            view.setSaveThis(GameState.PRE_PLAYING);
            System.out.println("The first playyyy");
        } catch (IOException | ClassNotFoundException e) {
            view = new View(curLevel);
            view.setSaveThis(GameState.PRE_PLAYING);
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

        try{
            switch (Controller.curGameState) {
                case GameState.PLAYING:
                    view.setSaveThis(GameState.PAUSE);
                    break;
                case GameState.LOSE:
                    view.setSaveThis(GameState.PRE_PLAYING);
                    break;
                case GameState.WIN:
                    view.setSaveThis(GameState.PRE_PLAYING);
                    break;
                case GameState.MENU:
                    break;
                default:
                    view.setSaveThis(curGameState);
                    break;
            }

            File file = new File("res/saved.data");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fileOutputStream);
            os.writeObject(view);
            os.close();
        } catch (IOException e) {
            System.out.println("Write object exception");
            e.printStackTrace();
        }

    }
}