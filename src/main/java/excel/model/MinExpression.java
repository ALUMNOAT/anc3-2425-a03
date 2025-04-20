package excel.model;

public class MinExpression extends Expression {
    private final String range;
    private final SpreadsheetModel spreadsheet;
    private final SpreadsheetCellModel currentCell;

    public MinExpression(String range, SpreadsheetModel spreadsheet, SpreadsheetCellModel currentCell) {
        this.range = range;
        this.spreadsheet = spreadsheet;
        this.currentCell = currentCell;
        System.out.println("MinExpression created with range: " + range);
    }

    @Override
    public Object interpret() {
        System.out.println("Interpreting MIN: " + range);

        // Vérification de la syntaxe
        String[] refs = range.split(":");
        if (refs.length != 2) return "#SYNTAX_ERROR";

        int[] startCoords = spreadsheet.parseCellReference(refs[0]);
        int[] endCoords = spreadsheet.parseCellReference(refs[1]);

        if (startCoords == null || endCoords == null || startCoords[0] > endCoords[0] || startCoords[1] > endCoords[1]) {
            return "#SYNTAX_ERROR";
        }

        // Vérification de la référence circulaire
        int[] currentCoords = {currentCell.getRow(), currentCell.getCol()};
        if (isCellInRange(currentCoords, startCoords, endCoords)) {
            System.out.println("Circular reference detected: " + range);
            return "#CIRCULAR REF";
        }

        // Vérifier si une cellule dans le range est une référence circulaire
        for (int row = startCoords[0]; row <= endCoords[0]; row++) {
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                String cellRef = spreadsheet.convertToReference(row, col);
                if (spreadsheet.hasCircularReference(cellRef)) {
                    System.out.println("Circular reference inside MIN range: " + cellRef);
                    return "#CIRCULAR REF";
                }
            }
        }

        // Calcul du minimum
        double min = Double.MAX_VALUE;
        for (int row = startCoords[0]; row <= endCoords[0]; row++) {
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                String cellRef = spreadsheet.convertToReference(row, col);
                currentCell.addRef(cellRef);
                Object cellValue = spreadsheet.getCellValue(cellRef);

                if (!(cellValue instanceof Number)) {
                    System.out.println("Invalid value in " + cellRef + ": " + cellValue);
                    return "#VALEUR";
                }

                min = Math.min(min, ((Number) cellValue).doubleValue());
            }
        }

        System.out.println("MIN result: " + min);
        return min;
    }

    private boolean isCellInRange(int[] cellCoords, int[] start, int[] end) {
        return cellCoords != null &&
                cellCoords[0] >= start[0] && cellCoords[0] <= end[0] &&
                cellCoords[1] >= start[1] && cellCoords[1] <= end[1];
    }
}