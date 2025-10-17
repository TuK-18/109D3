import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.stage.*;
import javafx.animation.AnimationTimer;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    public enum GameState {
        MENU,
        PLAYING,
        PAUSE,
        NEXT_LEVEL,
        TOTAL_WIN,
        LOSE
    }




    private Stage stage;
    public static GameState curGameState;

    private Scene menuScene;
    private  Scene playingScene;

    private PlayingSceneController playingSceneController;
    private MenuSceneController menuSceneController;

    private View view;

    /*public Controller(Stage stage_){
        this.stage = stage_;
        //view = new View();
    }*/

    private static Controller controllerInstance  = null;

    private Controller(Stage stage) {
        this.stage = stage;
    }

    public static Controller getControllerInstance(Stage stage) {
        controllerInstance = new Controller(stage);
        return controllerInstance;
    }


    public View getView() {
        return view;
    }

    public Stage getStage() {
        return stage;
    }

    AnimationTimer mainTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            update();
        }
    };

    public void run(){
        FXMLLoader loader1 = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("MenuScene.fxml")));

        FXMLLoader loader2 = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("PlayingScene.fxml")));


        try {
            menuScene = new Scene(loader1.load());
            playingScene = new Scene(loader2.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }



        playingSceneController = new PlayingSceneController(this);
        menuSceneController = new MenuSceneController(this);

        playingSceneController.setMenuScene(menuScene);
        menuSceneController.setPlayingScene(playingScene);

        curGameState = GameState.MENU;
        this.stage.setScene(menuScene);
        this.stage.show();

        mainTimer.start();

    }

    public void update() {
        playingSceneController.updateGameState();
        menuSceneController.updateGameState();
    }
}