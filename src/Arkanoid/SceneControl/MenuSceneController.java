package Arkanoid.SceneControl;

import Arkanoid.Controller;
import Arkanoid.SoundManager;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.EventHandler;

public class MenuSceneController extends SceneController {
    private Button resetButton;
    private Text menuText;

    public MenuSceneController() {
        super();
        this.scene = new Scene(root,800,500);

        menuText = new Text();

        this.resetButton = new Button();
        resetButton.setFont(Font.font(font.getFamily(), 50));

        root.getChildren().add(menuText);
        root.getChildren().add(resetButton);
        initialize();
    }

    @Override
    public void initialize() {

        menuText.setText("ARKANOID");
        menuText.setFont(Font.font(font.getFamily(), 100));
        menuText.setLayoutX(252);
        menuText.setLayoutY(97);

        sceneText.setText("ARKANOID");
        sceneText.setFont(Font.font(font.getFamily(), 100));
        sceneText.setFill(Color.GRAY);
        sceneText.setLayoutX(255);
        sceneText.setLayoutY(100);
        //sceneText.setLayoutX(10);
        //menuText.setLayoutX(200);

        playAgainButton.setText("PLAY");
        playAgainButton.setLayoutX(320);
        playAgainButton.setLayoutY(200);

        playAgainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Controller.curGameState == Controller.GameState.MENU) {
                    SoundManager.playClip1();
                    Controller.curGameState = Controller.GameState.PRE_PLAYING;
                }
            }
        });

        resetButton.setLayoutX(310);
        resetButton.setLayoutY(290);
        resetButton.setText("RESET");
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Controller.curGameState == Controller.GameState.MENU) {
                    SoundManager.playClip2();
                    Controller.curGameState = Controller.GameState.RESET;
                }
            }
        });
    }

    @Override
    public void showScene(Stage stage) {
        stage.setScene(this.scene);
    }
}