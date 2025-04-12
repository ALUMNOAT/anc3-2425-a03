package excel.viewmodel;

import excel.model.SpreadsheetCellModel;
import excel.model.SpreadsheetModel;
import javafx.beans.property.*;

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
