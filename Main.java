import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.*;

import javax.naming.ldap.Control;


public class Main extends Application {
    public static void main (String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setX(285);
        primaryStage.setY(0);
        example1(primaryStage);
    }

    private static void example1(Stage primaryStage) {
        //Data data = new Data(primaryStage,"Bouncing Balls");
        Controller vc = new Controller(primaryStage);
        vc.deepClean();
        vc.run();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                vc.getView().writeMapData();

                if (Controller.curGameState == Controller.GameState.LOSE
                        || Controller.curGameState == Controller.GameState.WIN) {
                    vc.deepClean();
                    vc.resetPlayerData();
                    vc.setCurPoint(0);

                }
                vc.writeData();
            }
        });
    }


}