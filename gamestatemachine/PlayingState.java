package gamestatemachine;

import objects.Box;
import graphic.Renderer;

public class PlayingState extends State {
    private GameStateMachine gsm;
    private Box box = new Box();



    public PlayingState(GameStateMachine gsm) {
        this.gsm = gsm;
    }

    @Override
    public void onEnter() {
        System.out.println("Entered Playing");
    }

    @Override
    public void handleInput(String a) {
//        System.out.println(a + "===="+ "\n");
        // ví dụ nhấn ESC:
        if(a.equals("m")){
            gsm.changeState(new MenuState(gsm));

        }
    }

    @Override
    public void update() {
        if (box.x < 400) {
            box.x++;
        }

        System.out.println("Game running...");
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawRectangle(box.x , box.y, box.width, box.height );
    }

    @Override
    public void onExit() {
        System.out.println("Leaving Playing");
    }
}
