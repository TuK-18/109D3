import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuSceneController extends SceneController {
    private Button resetButton;
    
    public MenuSceneController() {
        super();
        this.scene = new Scene(root,800,500);

        this.resetButton = new Button();
        resetButton.setFont(Font.font(font.getFamily(), 50));

        root.getChildren().add(resetButton);
        initialize();

    }

    @Override
    public void initialize() {

        sceneText.setText("ARKANOID");
        sceneText.setFont(Font.font(font.getFamily(), 100));
        sceneText.setLayoutX(255);
        sceneText.setLayoutY(100);
        
        playAgainButton.setText("PLAY");
        playAgainButton.setLayoutX(320);
        playAgainButton.setLayoutY(200);

        playAgainButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                
            }
        });

        resetButton.setLayoutX(310);
        resetButton.setLayoutY(290);
        resetButton.setText("RESET");
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                
            }
        });
    }

    @Override
    public void showScene(Stage stage) {
        stage.setScene(this.scene);
    }
}
