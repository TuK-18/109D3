//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javafx.application.Application;
import javafx.stage.*;

public class Main extends Application {
    public static void main (String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage) {
        example1(primaryStage);
    }
    private static void example1(Stage primaryStage) {
        //Data data = new Data(primaryStage,"Bouncing Balls");
        Controller vc = Controller.getControllerInstance(primaryStage);
        //vc.show();
        vc.run();
    }
}