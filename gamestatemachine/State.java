package gamestatemachine;
import graphic.Renderer;
public class State {
    public void onEnter(){};
    public void onExit(){};
    public void handleInput(String sc){};
    public void update(){};
    public void render(Renderer renderer){};
}
