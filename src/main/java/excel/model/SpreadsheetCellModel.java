package excel.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.DecimalFormat;

public class SpreadsheetCellModel {
    private final SimpleObjectProperty<String> valueProperty = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> resultProperty = new SimpleObjectProperty<>();
    private final int row;
    private  final int col;
    private final SpreadsheetModel spreadsheet;

    public SpreadsheetCellModel(String value, String result, int row, int col, SpreadsheetModel spreadsheet){
        this.valueProperty.set(value);
        this.resultProperty.set(result);
        this.row = row;
        this.col = col;
        this.spreadsheet = spreadsheet;
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

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    //POTENTIELLEMENT AJOUTER LE CALCUL ICI ??

    public String getCellReference() {
        char columnLetter = (char) ('A' + col); // Convert column index to letter
        int rowIndex = row + 1; // Convert zero-based index to one-based
        return String.valueOf(columnLetter) + rowIndex; // Combine to form "A1", "B2", etc.
    }


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
        if (value != null && !value.isEmpty()) {
            if (!value.startsWith("=")) {
                this.resultProperty.set(value);
                return;
            }

            spreadsheet.updateDependencies(getCellReference(), value);

            ExpressionBuilder interpreter = new ExpressionBuilder(this.spreadsheet);
            Expression exp = interpreter.build(value);

            if (exp != null) {
                Object result = exp.interpret(); // Can be a Double, String, etc.

                if (result instanceof Double) {
                    DecimalFormat df = new DecimalFormat("#.##");
                    String res = df.format(result).replace(',', '.'); // Ensure decimal point
                    this.resultProperty.set(res);
                } else if (result instanceof Boolean) {
                    // Handle boolean results
                    this.resultProperty.set(result.toString());
                } else if (result instanceof String) {
                    this.resultProperty.set(result.toString());
                } else {
                    // Handle other cases like error messages or strings
                    this.resultProperty.set(result != null ? result.toString() : "Erreur");
                }
            } else {
                this.resultProperty.set("#VALUE");
            }
        } else {
            this.resultProperty.set("");
        }
    }






}