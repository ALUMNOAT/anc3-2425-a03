package excel.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.DecimalFormat;

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

    /**
     * Met à jour le résultat de la cellule en évaluant son expression.
     * <p>
     * - Si l'entrée est une expression mathématique valide (commençant par '='), elle est évaluée et formatée :
     *     - Affiche un entier sans décimales si le résultat est un nombre entier.
     *     - Sinon, garde deux chiffres après la virgule.
     * - Si l'entrée est invalide, la cellule affiche "Erreur".
     * - Si l'entrée est vide ou null, le résultat est réinitialisé à une chaîne vide.
     */
    public void setResult(String value) {
        if (value != null) {
            ExpressionBuilder interpreter = new ExpressionBuilder();
            Expression exp = interpreter.build(value);

            if (exp != null) {
                DecimalFormat df = new DecimalFormat("#.##");
                String res = df.format(exp.interpret()).replace(',', '.'); // Assure le point comme séparateur décimal
                this.resultProperty.set(res);
            } else {
                this.resultProperty.set("Erreur");
            }
        } else {
            this.resultProperty.set("");
        }
    }


}