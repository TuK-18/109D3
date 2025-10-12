import gamestatemachine.*;

import graphic.Renderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(400, 600);
        Renderer renderer = new Renderer(canvas);
        App app = new App(renderer);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                app.runOneFrame(); // gọi mỗi frame ~60 lần/giây
            }
        }.start();

        stage.setTitle("My Game Window");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
