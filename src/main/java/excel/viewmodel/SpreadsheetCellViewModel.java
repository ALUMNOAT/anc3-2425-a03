package excel.viewmodel;

import excel.model.SpreadsheetCellModel;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.text.DecimalFormat;

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

    public String getCellResult() {
        String result = model.getResult(); // Récupère le résultat sous forme de String
//        System.out.println("Cell result: " + result); // Affiche le résultat dans la console
        // Si le résultat est un nombre (par exemple "4.0"), on le formate pour enlever la partie décimale si elle est inutile
        return formatResult(result);
    }

    private String formatResult(String result) {
        // Si le résultat est un nombre, on peut essayer de le convertir en Double
        try {
            Double d = Double.parseDouble(result);  // On tente de parser la valeur en Double
            // Vérifie si la partie décimale est zéro
            if (d % 1 == 0) {
                return String.valueOf(d.intValue());  // Retourne l'entier sans décimale
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(d).replace(',', '.');  // Utilisation du format avec 2 décimales
            }
        } catch (NumberFormatException e) {
            // Si ce n'est pas un nombre, on renvoie le résultat tel quel (il peut s'agir de chaînes comme "#ERREUR")
            return result;
        }
    }

    public void setCellValue(String value) {
        model.addValue(value);
    }

    public void setCellResult(String value) {
        model.setResult(value);
    }


}