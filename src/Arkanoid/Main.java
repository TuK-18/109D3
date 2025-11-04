package Arkanoid;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.*;


public class Main extends Application {

    public static void main (String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setX(285);
        primaryStage.setY(0);
        primaryStage.setTitle("ARKANOID");
        example1(primaryStage);
    }

    private static void example1(Stage primaryStage) {
        Controller vc = Controller.getInstance(primaryStage);
        //vc.deepClean();
        vc.run();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //vc.getView().writeMapData();

                if (Controller.curGameState == Controller.GameState.LOSE
                        || Controller.curGameState == Controller.GameState.WIN) {
                    /*vc.deepClean();
                    vc.resetPlayerData();
                    vc.setCurPoint(0);*/
                    vc.reset();
                }
                vc.writeData();
            }
        });
    }

}

