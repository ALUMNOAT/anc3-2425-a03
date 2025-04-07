package excel.model;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Stack;

public class CommandManager {
    private static final CommandManager instance = new CommandManager();

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private final SimpleBooleanProperty canUndo = new SimpleBooleanProperty();
    private final SimpleBooleanProperty canRedo = new SimpleBooleanProperty();

    public static CommandManager getInstance() {
        return instance;
    }

    // utilisé pour le disable du undo
    public ReadOnlyBooleanProperty canUndoProperty() {
        return canUndo;
    }

    // utilisé pour le disable du redo
    public ReadOnlyBooleanProperty canRedoProperty() {
        return canRedo;
    }

    public void updateButtons() {
        canUndo.set(!undoStack.isEmpty());
        canRedo.set(!redoStack.isEmpty());
    }

    //videle stack du redo si l'on ajoute une nouvelle valeur
    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        updateButtons();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            System.out.println("undo");
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            updateButtons();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            System.out.println("redo");
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            updateButtons();
        }
    }
}