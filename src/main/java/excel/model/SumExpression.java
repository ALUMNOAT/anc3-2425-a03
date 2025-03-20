package excel.model;

import java.util.HashSet;
import java.util.Set;

public class SumExpression extends Expression {
    private final String range;
    private final SpreadsheetModel spreadsheet;

    public SumExpression(String range, SpreadsheetModel spreadsheet) {
        this.range = range;
        this.spreadsheet = spreadsheet;
        System.out.println("SumExpression created with range: " + this.range);
    }

    @Override
    public Object interpret() {
        System.out.println("Range: " + range); // DEBUG

        String[] parts = range.split(":");
        if (parts.length != 2) return "SYNTAX_ERROR";

        int[] start = parseCellReference(parts[0]);
        int[] end = parseCellReference(parts[1]);

        if (start == null || end == null || (start[0] > end[0] || start[1] > end[1])) {
            return "SYNTAX_ERROR";
        }

        // Vérifier référence circulaire
        if (spreadsheet.hasCircularReference(parts[0]) || spreadsheet.hasCircularReference(parts[1])) {
            return "#CIRCULAR_REF";
        }

        double sum = 0;
        Set<String> visited = new HashSet<>();

        for (int row = start[0]; row <= end[0]; row++) {
            for (int col = start[1]; col <= end[1]; col++) {
                String ref = convertToReference(row, col);
                System.out.println("Processing: " + ref); // DEBUG

                if (!visited.add(ref)) {
                    System.out.println("Skipping duplicate: " + ref);
                    continue;
                }

                Object value = spreadsheet.getCellValue(ref);
                System.out.println("Value at " + ref + " = " + value); // DEBUG

                if (value instanceof String && isNumber((String) value)) {
                    value = Double.parseDouble((String) value);
                }
                if (!(value instanceof Double)) return "#VALEUR";
                sum += (Double) value;
            }
        }

        return sum;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private int[] parseCellReference(String ref) {
        if (!ref.matches("[A-Z]+[0-9]+")) return null;

        int row = 0, col = 0;
        int i = 0;

        // 🔹 Lire les lettres (colonne)
        while (i < ref.length() && Character.isLetter(ref.charAt(i))) {
            col = col * 26 + (ref.charAt(i) - 'A' + 1); // Correction
            i++;
        }

        // 🔹 Lire les chiffres (ligne)
        while (i < ref.length() && Character.isDigit(ref.charAt(i))) {
            row = row * 10 + (ref.charAt(i) - '0');
            i++;
        }

        return new int[]{row - 1, col - 1}; // Indices commencent à 0
    }


    private String convertToReference(int row, int col) {
        StringBuilder colName = new StringBuilder();
        col++; // Excel starts from 1, but our col index starts from 0

        while (col > 0) {
            col--; // Adjust for 0-based index
            colName.insert(0, (char) ('A' + col % 26));
            col /= 26;
        }

        return colName + String.valueOf(row + 1);
    }

}
