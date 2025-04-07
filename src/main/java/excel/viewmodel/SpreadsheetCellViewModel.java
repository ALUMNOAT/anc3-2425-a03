package excel.viewmodel;

import excel.model.SpreadsheetCellModel;
import javafx.beans.property.ReadOnlyObjectProperty;

public class SpreadsheetCellViewModel {
    private final SpreadsheetCellModel model;

    //OK

    public SpreadsheetCellViewModel(SpreadsheetCellModel model) {
        this.model = model;
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty() {
        return model.valueProperty();
    }

    public ReadOnlyObjectProperty<String> getCellResultProperty() {
        return model.resultProperty();
    }

    public String getCellValue(){
        return  model.getValue();
    }

    public String getCellResult(){
        return  model.getResult();
    }

    public void setCellValue(String value) {
        model.addValue(value);
    }

    public void setCellResult(String value) {
        model.setResult(value);
    }


}