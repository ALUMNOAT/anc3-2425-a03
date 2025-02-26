package excel.model;


import excel.viewmodel.SpreadsheetCellViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpreadsheetModel {
    private final int rows;
    private final int cols;
    private final ObjectProperty<SpreadsheetCellViewModel> selectedCell = new SimpleObjectProperty<>();
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList(
            item -> new Observable[]{});

    public SpreadsheetModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        for (int i = 0 ; i< rows; i++){
            final ObservableList<SpreadsheetCellModel> list = FXCollections.observableArrayList();
            for (int j = 0 ; j < cols; j++){
                SpreadsheetCellModel cell = new SpreadsheetCellModel("","",i,j);
                list.add(cell);
            }
            data.add(list);

        }
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


    public SpreadsheetCellViewModel getCellViewModel(int line, int column) {
        return new SpreadsheetCellViewModel(getCell(line, column));
    }

    public void setCellValue(int line, int column, String value) {
        System.out.println("value : " + value);
        getCellViewModel(line, column).setCellValue(value);
        if (!value.isEmpty()){
            Builder interpreter = new Builder(value);
            int result = interpreter.interpret((String) value);
            String res = Integer.toString(result);
            System.out.println("res : " + res);
            getCell(line,column).setResult(res);
        }else {
            System.out.println("res1 : " + value);
            getCell(line, column).setResult(value);
        }
    }
    public void setCellResult(int line, int column, String value) {
        getCellViewModel(line, column).setCellResult(value);
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
        return getCellViewModel(line, column).getCellValueProperty();
    }



    public ReadOnlyObjectProperty<String> getCellResultProperty(int line, int column) {
        return getCellViewModel(line, column).getCellResultProperty();
    }

    public ObservableList<ObservableList<SpreadsheetCellModel>> getData() {
        return data;
    }

    public ObjectProperty<SpreadsheetCellViewModel> selectedCellProperty() { //cellule selectionnée
        return selectedCell;
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        this.selectedCell.set(getCellViewModel(row, column));
    }
}
