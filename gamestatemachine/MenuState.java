package gamestatemachine;
import graphic.Renderer;
public class MenuState extends State {
    private GameStateMachine gsm;

    public MenuState(GameStateMachine gsm) {
        this.gsm = gsm;
    }

    @Override
    public void onEnter() {
        System.out.println("Entered Menu");
    }

    @Override
    public void handleInput(String a) {

//        System.out.println(a + "===="+ "\n");
        if(a.equals("p")){
            gsm.changeState(new PlayingState(gsm));

        }
    }

    @Override
    public void update() {}

    @Override
    public void render(Renderer renderer) {
        System.out.println("Rendering Menu...");
    }

    @Override
    public void onExit() {
        System.out.println("Leaving Menu");
    }
}
