package gamestatemachine;
import java.util.Stack;
import graphic.Renderer;


public class GameStateMachine {
    private final Stack<State> stateStack = new Stack<>();

    public void changeState(State newState){

        if (stateStack.isEmpty()) {
            stateStack.push(newState);
            stateStack.peek().onEnter();
        } else {
            stateStack.peek().onExit();
            stateStack.pop();
            stateStack.push(newState);
            stateStack.peek().onEnter();
        }
    }

    public void popState(){
        stateStack.peek().onExit();
        stateStack.pop();
    }

    public void pushState(State newState){
        stateStack.push(newState);
        stateStack.peek().onEnter();
    }

    public void update(){
        stateStack.peek().update();
    }

    public void render(Renderer renderer){
        stateStack.peek().render(renderer);
    }


    public void handleInput(String sc){


        stateStack.peek().handleInput(sc);

    }

}
