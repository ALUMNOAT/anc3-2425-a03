package excel.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SpreadsheetCellModel {
    private final SimpleObjectProperty<String> valueProperty = new SimpleObjectProperty<>();
    private final int row;
    private  final int col;

    public SpreadsheetCellModel(String value, int row, int col){
        this.valueProperty.set(value);
        this.row = row;
        this.col = col;
    }

    public ReadOnlyObjectProperty<String> valueProperty(){ return this.valueProperty;}

    public void set(String value) {
        this.valueProperty.set(value);
    }
}