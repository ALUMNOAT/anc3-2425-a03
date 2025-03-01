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

    //POTENTIELLEMENT AJOUTER LE CALCUL ICI ??
    public void setResult(String value) {
        if (value != null) {
            ExpressionBuilder interpreter = new ExpressionBuilder();
            Expression exp = interpreter.build(value);

            if (exp != null) {
                String res = String.valueOf(exp.interpret());
                this.resultProperty.set(res);
            } else {
                this.resultProperty.set("Erreur");
            }
        } else {
            this.resultProperty.set("");
        }
    }


}