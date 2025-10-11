
import javafx.util.Duration;
import javafx.animation.*;
import javafx.stage.*;

public class Controller {
    private Stage stage;
    private View view;

    public Controller(Stage stage_){
        this.stage = stage_;
        view = new View();
    }

    public void show(){
        this.view.show(stage,this.view.getScene());
        animate();
    }

    public void animate() {
        KeyFrame k = new KeyFrame(Duration.millis(10), e ->
        {
            for (GameObject b : this.view.getBalls() ) {
                if(b instanceof Ball) {
                    b.move();
                } /*else {
                    b.move();
                }*/
                //b.move();

                auxAnimate(b);
            }
        });
        Timeline t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void auxAnimate(GameObject ball) {
        for (GameObject b : this.view.getBalls()) {

            ball.detectCollision(b);
        }
    }
}