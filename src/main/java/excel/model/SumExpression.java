package excel.model;

public class SumExpression extends Expression {
    private final String range;
    private final SpreadsheetModel spreadsheet;
    private final SpreadsheetCellModel currentCell;

    public SumExpression(String range, SpreadsheetModel spreadsheet, SpreadsheetCellModel currentCell) {
        this.range = range;
        this.spreadsheet = spreadsheet;
        this.currentCell = currentCell;
        //System.out.println("SumExpression created with range: " + range);
    }

    @Override
    public Object interpret() {
        //System.out.println("Interpreting SUM: " + range);

        // Vérification de la syntaxe
        String[] refs = range.split(":");
        if (refs.length != 2) return "#SYNTAX_ERROR";

        int[] startCoords = spreadsheet.parseCellReference(refs[0]);
        int[] endCoords = spreadsheet.parseCellReference(refs[1]);

        /**
         * Vérifie la validité syntaxique de la plage de cellules donnée pour la fonction SUM.
         *
         * startCoords == null : la cellule de début est invalide ou mal interprétée
         * (ex : =SUM(A1:ABC) → "ABC" n'est pas une cellule valide).
         *
         * endCoords == null : la cellule de fin est invalide.
         *
         * startCoords[0] > endCoords[0] : la ligne de début est après la ligne de fin
         * (ex : =SUM(A5:A2)).
         *
         * startCoords[1] > endCoords[1] : la colonne de début est après la colonne de fin
         * (ex : =SUM(D1:A1)).
         */

        if (startCoords == null || endCoords == null || startCoords[0] > endCoords[0] || startCoords[1] > endCoords[1]) {
            return "#SYNTAX_ERROR";
        }

        // Vérification de la référence circulaire
        int[] currentCoords = {currentCell.getRow(),currentCell.getCol()};
        if (isCellInRange(currentCoords, startCoords, endCoords)) {
            //System.out.println("Circular reference detected: " + range);
            return "#CIRCULAR REF";
        }

        // Vérifier si une cellule dans le range est une référence circulaire
        for (int row = startCoords[0]; row <= endCoords[0]; row++) {
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                String cellRef = spreadsheet.convertToReference(row, col);
                if (spreadsheet.hasCircularReference(cellRef)) {
                    //System.out.println("Circular reference inside SUM range: " + cellRef);
                    return "#CIRCULAR REF";
                }
            }
        }

        // Calcul de la somme
        double sum = 0;
        for (int row = startCoords[0]; row <= endCoords[0]; row++) {
            for (int col = startCoords[1]; col <= endCoords[1]; col++) {
                String cellRef = spreadsheet.convertToReference(row, col);
                currentCell.addRef(cellRef);
                Object cellValue = spreadsheet.getCellValue(cellRef);

                if (!(cellValue instanceof Number)) {
                    //System.out.println("Invalid value in " + cellRef + ": " + cellValue);
                    return "#VALEUR";
                }

                sum += ((Number) cellValue).doubleValue();
            }
        }

        //System.out.println("SUM result: " + sum);
        return sum;
    }

    private boolean isCellInRange(int[] cellCoords, int[] start, int[] end) {
        return cellCoords != null &&
                cellCoords[0] >= start[0] && cellCoords[0] <= end[0] &&
                cellCoords[1] >= start[1] && cellCoords[1] <= end[1];
    }
}

