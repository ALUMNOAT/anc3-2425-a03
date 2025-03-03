package excel.model;

public class ReferenceExpression extends Expression {
    private final String cellReference;
    private final SpreadsheetModel spreadsheet; // Accès direct au modèle

    public ReferenceExpression(String cellReference, SpreadsheetModel spreadsheet) {
        this.cellReference = cellReference;
        this.spreadsheet = spreadsheet;
    }

    public Object interpret() {
        String value = String.valueOf(spreadsheet.getCellValue(cellReference)); // Fetch cell content
        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return "#VALEUR"; // Error handling
        }
    }


}


