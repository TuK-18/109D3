
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.stage.*;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class Controller {
    private Stage stage;
    private View view;
    private CollisionManager collisionManager;
    public Controller(Stage stage_){
        this.stage = stage_;
        view = new View();
        collisionManager = new CollisionManager();
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
                //auxAnimate(b);
                b.detectCollision(view.getPlatform());

                for (int i = 0; i < view.getBricks().size(); i++) {
                    if (b.detectCollision(view.getBricks().get(i))) {
                        view.removeFromWorld(view.getBricks().get(i));
                        i--;
                    }
                }

            }
            this.view.getPlatform().move();

        });

        Timeline t = new Timeline(k);
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void auxAnimate(GameObject ball) {
        for (Ball b : this.view.getBalls()) {

            ball.detectCollision(b);
        }
    }

    private void axuAnimate(Platform platform) {
        for (Ball b : this.view.getBalls()) {
            b.detectCollision(platform);
        }
    }
}