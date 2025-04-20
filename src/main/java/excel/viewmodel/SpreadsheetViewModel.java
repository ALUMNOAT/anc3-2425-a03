package excel.viewmodel;

import excel.model.CommandManager;
import excel.model.SpreadsheetCellModel;
import excel.model.SpreadsheetModel;
import javafx.beans.property.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SpreadsheetViewModel {
    private final SpreadsheetModel model;
    private final SimpleBooleanProperty editableProperty = new SimpleBooleanProperty(true);
    // Référence de la cellule sélectionnée (ex. "A1")
    private final StringProperty selectedCellReference = new SimpleStringProperty();

    public SpreadsheetViewModel(SpreadsheetModel model) {
        this.model = model;
        // listener pour mettre à jour la référence lorsque la cellule sélectionnée change
        selectedCellProperty().addListener(
                (obs,
                 oldVal, newVal) -> updateSelectedCellReference());
    }

    public SpreadsheetCellViewModel getCellViewModel(int line, int column) {
        return new SpreadsheetCellViewModel(model.getCell(line,column));
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
        return getCellViewModel(line,column).getCellValueProperty();
    }

    public ReadOnlyObjectProperty<String> getCellResultProperty(int line, int column) {
        return getCellViewModel(line,column).getCellResultProperty();
    }

    public String getCellValue(int line, int column) {
        return getCellViewModel(line,column).getCellValue();
    }

    public String getCellResult(int line, int column) {
        return getCellViewModel(line,column).getCellResult();
    }

    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public void setCellValue(int line, int column, String value) {
        getCellViewModel(line,column).setCellValue(value);
    }


    public void setCellResult(int line, int column, String value){
        getCellViewModel(line,column).setCellResult(value);
    }

    public void setDimensions(int rows, int cols) {
        model.setRows(rows);
        model.setCols(cols);
    }

    public void open(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] dimensions = line.split(",");
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);
                setDimensions(rows, cols);

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    String[] position = parts[0].split(",");
                    int row = Integer.parseInt(position[0]);
                    int col = Integer.parseInt(position[1]);
                    String value = parts[1];
                    setCellValue(row, col, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectProperty<SpreadsheetCellModel> selectedCellProperty() { //cellule selectionnée
        return model.selectedCellProperty();
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        model.setSelectedCell(row, column);
    }

    public SpreadsheetCellModel getSelectedCell(){
        return model.selectedCellProperty().get();
    }

    public int getSelectedCellRow(){
        return model.selectedCellProperty().get().getRow();
    }

    public int getSelectedCellColumn(){
        return model.selectedCellProperty().get().getCol();
    }

    public void undo() {
        CommandManager.getInstance().undo();
    }

    public void redo() {
        CommandManager.getInstance().redo();
    }

    public ReadOnlyBooleanProperty canUndo() {
        return CommandManager.getInstance().canUndoProperty();
    }

    public ReadOnlyBooleanProperty canRedo() {
        return CommandManager.getInstance().canRedoProperty();
    }

    // Convertit une position (ligne, colonne) en référence de cellule (ex. "A1")
    String convertToReference(int row, int col) {
        StringBuilder colName = new StringBuilder();
        col++;

        while (col > 0) {
            col--;
            colName.insert(0, (char) ('A' + col % 26));
            col /= 26;
        }

        return colName.toString() + (row + 1);

    }

    // Propriété de la référence de la cellule sélectionnée
    public StringProperty selectedCellReferenceProperty() {
        return selectedCellReference;
    }

    // Met à jour la référence de la cellule sélectionnée
    public void updateSelectedCellReference() {
        String reference = getSelectedCellReference();
        selectedCellReference.set(reference != null ? reference : "");
    }

    // Retourne la référence de la cellule sélectionnée
    public String getSelectedCellReference() {
        SpreadsheetCellModel selected = getSelectedCell();
        if (selected == null) return null;

        int row = getSelectedCellRow();
        int col = getSelectedCellColumn();
        // Convert the row and column to a cell reference (e.g., A1, B2, etc.)
        return "Cellule : " + convertToReference(row, col);
    }

    // Efface toutes les cellules du tableur
    public void clearAllCells() {
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                if (!getCellValue(row, col).isEmpty()) {
                    setCellValue(row, col, "");
                }
            }
        }
    }
}
