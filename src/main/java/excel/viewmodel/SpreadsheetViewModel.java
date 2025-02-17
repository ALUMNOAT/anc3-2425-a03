package excel.viewmodel;

import excel.model.SpreadsheetModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class SpreadsheetViewModel {
    private final SpreadsheetModel model;
    private final ObjectProperty<SpreadsheetCellViewModel> selectedCell = new SimpleObjectProperty<>();

    public SpreadsheetViewModel(SpreadsheetModel model) {
        this.model = model;
    }

    private SpreadsheetCellViewModel getCellViewModel(int line, int column) {
        return new SpreadsheetCellViewModel(model.getCell(line, column));
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
        return getCellViewModel(line, column).getCellValueProperty();
    }

    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public void setCellValue(int line, int column, String value) {
        getCellViewModel(line, column).setCellValue(value);
    }

    public ObjectProperty<SpreadsheetCellViewModel> selectedCellProperty() { //cellule selectionnée
        return selectedCell;
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        this.selectedCell.set(getCellViewModel(row, column));
    }
}
