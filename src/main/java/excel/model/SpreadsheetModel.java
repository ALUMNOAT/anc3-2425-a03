package excel.model;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpreadsheetModel {
    private final int rows;
    private final int cols;
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList(
            item -> new Observable[]{});

    public SpreadsheetModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        for (int i = 0 ; i< rows; i++){
            final ObservableList<SpreadsheetCellModel> list = FXCollections.observableArrayList();
            for (int j = 0 ; j < cols; j++){
                SpreadsheetCellModel cell = new SpreadsheetCellModel("",i,j);
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


    public ObservableList<ObservableList<SpreadsheetCellModel>> getData() {
        return data;
    }
}
