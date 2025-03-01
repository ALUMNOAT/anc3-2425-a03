package excel.viewmodel;

import excel.model.SpreadsheetCellModel;
import excel.model.SpreadsheetModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

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

    public ReadOnlyBooleanProperty editableProperty() {
        return this.editableProperty;
    }

    public void toggleEditable() {
        this.editableProperty.set(this.editableProperty.not().get());
    }
}
