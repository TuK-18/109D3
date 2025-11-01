import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SceneController{


    protected Scene scene;

    protected Controller controller;


    protected Group root;


    protected Button playAgainButton;


    protected ImageView imageView;

    protected Text sceneText;
    protected Text highScoreText;
    protected Text lastScoreText;
    protected Font font;

    protected int lastScore;
    protected int highScore;

    public SceneController() {
        //root = new Group();
        this.root = new Group();
        this.playAgainButton = new Button();
        this.imageView = new ImageView();
        this.lastScoreText = new Text();
        this.highScoreText = new Text();
        this.sceneText = new Text();
        InputStream fontStream = getClass().getResourceAsStream("fontName.ttf");
        font = Font.loadFont(fontStream, 50);
        playAgainButton.setFont(font);
        sceneText.setFont(Font.font(font.getFamily(), 60));
        lastScoreText.setFont(Font.font(font.getFamily(), 30));
        highScoreText.setFont(Font.font(font.getFamily(), 30));

        root.getChildren().add(playAgainButton);
        root.getChildren().add(imageView);
        root.getChildren().add(lastScoreText);
        root.getChildren().add(highScoreText);
        root.getChildren().add(sceneText);
        //this.scene = new Scene(root,800,640);
    }

    public void showScene(Stage stage){
        //this.playAgainButton = new Button();
        lastScoreText.setText("YOUR SCORE\n" + lastScore);
        highScoreText.setText("HIGH SCORE \n" + highScore);
        stage.setScene(this.scene);
        //stage.show();
    }

    public Scene getScene() {
        return this.scene;
    }

    /*public void setController(Controller controller) {
        this.controller = controller;
    }*/

    public void initialize(){

    }

    public void setLastScore(int x) {
        this.lastScore = x;
    }

    public void setHighScore(int x) {
        this.highScore = x;
    }

}