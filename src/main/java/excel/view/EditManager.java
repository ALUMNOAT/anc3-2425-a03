package excel.view;

import java.util.Stack;

public class EditManager {
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private String currentState = "";

    public void setText(String newText) {
        undoStack.push(currentState);
        currentState = newText;
        redoStack.clear(); // Réinitialise redo lorsqu'on fait une nouvelle action
    }

    public String undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentState);
            currentState = undoStack.pop();
        }
        return currentState;
    }

    public String redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentState);
            currentState = redoStack.pop();
        }
        return currentState;
    }

    public String getText() {
        return currentState;
    }
}
