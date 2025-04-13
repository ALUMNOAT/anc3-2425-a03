package excel.viewmodel;

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
    public SpreadsheetViewModel(SpreadsheetModel model) {
        this.model = model;
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

    public void undo (){
        model.undo();
    }

    public void redo(){
        model.redo();
    }

    public ReadOnlyBooleanProperty canRedo(){
        return model.canRedo();
    }
    public ReadOnlyBooleanProperty canUndo(){
        return model.canUndo();
    }


}
