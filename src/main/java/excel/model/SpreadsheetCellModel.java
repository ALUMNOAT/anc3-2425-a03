package excel.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SpreadsheetCellModel {
    private final SimpleObjectProperty<String> valueProperty = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> resultProperty = new SimpleObjectProperty<>();
    private final int row;
    private  final int col;

    public SpreadsheetCellModel(String value, String result, int row, int col){
        this.valueProperty.set(value);
        this.resultProperty.set(result);
        this.row = row;
        this.col = col;
    }

    public ReadOnlyObjectProperty<String> valueProperty(){ return this.valueProperty;}

    public ReadOnlyObjectProperty<String> resultProperty(){ return this.resultProperty;}

    public String getValue(){
        return valueProperty.get();
    }

    public String getResult(){
        return resultProperty.get();
    }

    public void setValue(String value) {
        this.valueProperty.set(value);
    }

    public void setResult(String value){
        this.resultProperty.set(value);
    }
}