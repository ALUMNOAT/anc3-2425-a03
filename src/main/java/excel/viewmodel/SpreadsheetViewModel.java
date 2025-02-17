package excel.viewmodel;

import excel.model.SpreadsheetModel;
import javafx.beans.property.*;

public class SpreadsheetViewModel {
    private final SpreadsheetModel model;

    public SpreadsheetViewModel(SpreadsheetModel model) {
        this.model = model;
    }

    private SpreadsheetCellViewModel getCellViewModel(int line, int column) {
        return model.getCellViewModel(line, column);
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
        return model.getCellValueProperty(line, column);
    }

    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public void setCellValue(int line, int column, String value) {
        model.setCellValue(line, column, value);
    }

    public ObjectProperty<SpreadsheetCellViewModel> selectedCellProperty() { //cellule selectionnée
        return model.selectedCellProperty();
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        model.setSelectedCell(row, column);
    }
}
