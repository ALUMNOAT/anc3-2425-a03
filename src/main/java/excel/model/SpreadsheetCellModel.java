package excel.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpreadsheetCellModel {
    private final SimpleObjectProperty<String> valueProperty = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> resultProperty = new SimpleObjectProperty<>();
    // changer en liste de Listener ? + Ajout d'un remove all de la liste en début de setResult ? (pour effacer les refs supprimées)
    private final List<SpreadsheetCellModel> referencing = new ArrayList<>();
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


    // ajoute dans le stack si la valeur est différente de l'ancienne
    public void addValue(String value) {
        // évite d'ajouter dans le stack un changement inexistant ou si l'on a une des deux valeurs (oldValue et newValue) à null et l'autre à ""
        if (Objects.equals(this.getValue(),value) || (this.getValue() == null && Objects.equals(value, "")) || (Objects.equals(this.getValue(), "") && Objects.equals(value, null))){
            return;
        }
        CommandManager.getInstance().execute(new SetValueOperation(this, value));
        spreadsheet.updateDependencies(getCellReference(), value); // Met à jour les dépendances de la cellule
    }

    // Applique la valeur au valueProperty
    public void setValue(String value){
        setResult(value);
        System.out.println("value = " + value);
        this.valueProperty.set(value);
    }

//    public void addRef(String ref){
//        // changer en liste de Listener sur le valueProperty ? + Ajout d'un remove all de la liste en début de setResult ? (pour effacer les refs supprimées)
//        referencing.add(ref);
//
//    }


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
        if (value == null || value.isEmpty()) {
            this.resultProperty.set("");
            return;
        }

        if (!value.startsWith("=")) {
            value = value.replaceAll("(?<=\\d),(?=\\d)", "."); // ✅ Convertit 5,3 → 5.3
            this.resultProperty.set(value);
            return;
        }


        spreadsheet.updateDependencies(getCellReference(), value);

        ExpressionBuilder interpreter = new ExpressionBuilder(this.spreadsheet, this.col, this.row);
        Expression exp = interpreter.build(value);

        if (exp != null) {
            Object result = exp.interpret();
            this.resultProperty.set(formatResult(result));
        } else {
            this.resultProperty.set("SYNTAX_ERROR");
        }
    }

    private String formatResult(Object result) {
        if (result instanceof Double) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(result).replace(',', '.'); // Ensure decimal point
        } else if (result instanceof Boolean || result instanceof String) {
            return result.toString();
        }
        return result != null ? result.toString() : "#Valeur"; // Handle null or unexpected results
    }







}