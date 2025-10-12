package gamestatemachine;

import graphic.Renderer;

public final class App {
    private Renderer renderer;
    private GameStateMachine gsm;

    public App(Renderer renderer) {
        this.renderer = renderer;
        this.gsm = new GameStateMachine();
        gsm.changeState(new PlayingState(gsm));
    }

    // gọi mỗi frame
    public void runOneFrame() {
        gsm.update();       // update logic game
        gsm.render(renderer); // vẽ lại frame

    }
}
