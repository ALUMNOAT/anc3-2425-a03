package excel.viewmodel;

import excel.model.SpreadsheetCellModel;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.text.DecimalFormat;

public class SpreadsheetCellViewModel {
    private final SpreadsheetCellModel model;

    public SpreadsheetCellViewModel(SpreadsheetCellModel model) {
        this.model = model;
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty() {
        return model.valueProperty();
    }

    public ReadOnlyObjectProperty<String> getCellResultProperty() {
        return model.resultProperty();
    }

    public String getCellValue() {
        return model.getValue();
    }

    public String getCellResult() {
        String result = model.getResult(); // Récupère le résultat sous forme de String
        return formatResult(result);
    }

    public static String formatResult(Object result) {
        if (result instanceof Double) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(result).replace(',', '.');  // Assure que le séparateur décimal est correct
        } else if (result instanceof Boolean || result instanceof String) {
            return result.toString();
        }
        return result != null ? result.toString() : "#Valeur"; // Handle null or unexpected results
    }

    public void setCellValue(String value) {
        model.addValue(value);
    }

    public void setCellResult(String value) {
        model.setResult(value);
    }
}
