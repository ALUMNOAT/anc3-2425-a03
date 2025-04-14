package excel.model;


public class SetValueOperation implements Command {
    private final SpreadsheetCellModel cell;
    private final String newValue;
    private final String oldValue;

    public SetValueOperation(SpreadsheetCellModel cell, String newValue) {
        this.cell = cell;
        this.oldValue = cell.getValue();
        this.newValue = newValue;
        //System.out.println("SetValueOperation created: oldValue=" + oldValue + ", newValue=" + newValue);
    }

    //utilisé dans le cas de l'ajout d'une nouvelle valeur ou d'un redo
    @Override
    public void execute() {
        cell.setValue(newValue);
    }

    // utilisé pour le undo
    public void undo() {
        cell.setValue(oldValue);
    }
}