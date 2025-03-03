package excel.model;

import excel.viewmodel.SpreadsheetCellViewModel;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.IntStream;


public class SpreadsheetModel {
    private final int rows;
    private final int cols;
    private final ObjectProperty<SpreadsheetCellModel> selectedCell = new SimpleObjectProperty<>();
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList(
            item -> new Observable[]{});

    public SpreadsheetModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        IntStream.range(0, rows).forEach(a -> addNewRow());
    }

    private void addNewRow() {
        ObservableList<SpreadsheetCellModel> newRow = FXCollections.observableArrayList();
        for (int column = 0; column < cols; column++) {
            newRow.add(new SpreadsheetCellModel("", "",data.size(), column));
        }
        data.add(newRow);
    }

    public int getRowCount() {
        return this.rows;
    }

    public int getColumnCount() {
        return this.cols;
    }

    public SpreadsheetCellModel getCell(int line, int column) {
        return data.get(line).get(column);
    }


//    public void setCellValue(int line, int column, String value) {
//        getCellViewModel(line, column).setCellValue(value);
//    }
//
//    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
//        return getCellViewModel(line, column).getCellValueProperty();
//    }

    public ObservableList<ObservableList<SpreadsheetCellModel>> getData() {
        return data;
    }

    public ObjectProperty<SpreadsheetCellModel> selectedCellProperty() { //cellule selectionnée
        return selectedCell;
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        this.selectedCell.set(getCell(row,column));
    }


}
