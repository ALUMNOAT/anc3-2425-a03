package excel.model;

public class ReferenceExpression extends Expression {
    private final String cellReference;
    private final SpreadsheetModel spreadsheet; // Accès direct au modèle

    public ReferenceExpression(String cellReference, SpreadsheetModel spreadsheet) {
        this.cellReference = cellReference;
        this.spreadsheet = spreadsheet;
    }

    @Override
    public double interpret() {
        return spreadsheet.getCellValue(cellReference); // Demande la valeur directement
    }
}


