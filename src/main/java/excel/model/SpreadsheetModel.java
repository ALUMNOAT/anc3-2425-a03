package excel.model;

import excel.viewmodel.SpreadsheetCellViewModel;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.IntStream;


public class SpreadsheetModel {
    private final int rows;
    private final int cols;
    private final ObjectProperty<SpreadsheetCellModel> selectedCell = new SimpleObjectProperty<>();
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList(
            item -> new Observable[]{});

    private final Map<String, Set<String>> dependencies = new HashMap<>();

    public SpreadsheetModel(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        IntStream.range(0, rows).forEach(a -> addNewRow());
    }

    private void addNewRow() {
        ObservableList<SpreadsheetCellModel> newRow = FXCollections.observableArrayList();
        for (int column = 0; column < cols; column++) {
            newRow.add(new SpreadsheetCellModel("", "",data.size(), column, this));
        }
        data.add(newRow);
    }

    public int getRowCount() {
        return this.rows;
    }

    public int getColumnCount() {
        return this.cols;
    }

    public SpreadsheetCellModel getCell(int line, int column) {
        return data.get(line).get(column);
    }


//    public void setCellValue(int line, int column, String value) {
//        getCellViewModel(line, column).setCellValue(value);
//    }
//
//    public ReadOnlyObjectProperty<String> getCellValueProperty(int line, int column) {
//        return getCellViewModel(line, column).getCellValueProperty();
//    }

    public ObservableList<ObservableList<SpreadsheetCellModel>> getData() {
        return data;
    }

    public ObjectProperty<SpreadsheetCellModel> selectedCellProperty() { //cellule selectionnée
        return selectedCell;
    }

    public void setSelectedCell(int row, int column) {  // change la cellule selectionnée
        this.selectedCell.set(getCell(row,column));
    }


    public Object getCellValue(String cellReference) {
        if (hasCircularReference(cellReference)) {
            return "#CIRCULAR REF"; // Cycle detected
        }

        int[] coords = parseCellReference(cellReference);
        if (coords == null) return "#VALEUR"; // Return error if invalid reference
        SpreadsheetCellModel cell = getCell(coords[0], coords[1]);
        String result = cell.getResult(); // Assuming getResult() returns the cell value

        return parseResult(result);
    }

    private Object parseResult(String result) {
        if (result.equalsIgnoreCase("true")) return true;
        if (result.equalsIgnoreCase("false")) return false;

        try {
            return Double.parseDouble(result);
        } catch (NumberFormatException e) {
            return "#VALEUR"; // Return error for non-numeric results
        }
    }




    int[] parseCellReference(String reference) {
        if (!reference.matches("[A-Z]+[0-9]+")) return null; // Vérifie la syntaxe (ex: "A1")

        int column = 0;
        int rowIndex = 0;

        for (char c : reference.toCharArray()) {
            if (Character.isLetter(c)) {
                column = column * 26 + (c - 'A'); // Convertit "A" → 0, "B" → 1, ..., "Z" → 25
            } else {
                rowIndex = rowIndex * 10 + (c - '0'); // Récupère l'index de la ligne
            }
        }

        return new int[]{rowIndex - 1, column}; // Indices commencent à 0
    }

    public void updateDependencies(String cell, String formula) {
        // Remove old dependencies of the cell
        dependencies.remove(cell);

        // If the formula is valid, extract and store new dependencies
        if (formula != null && !formula.isEmpty()) {
            dependencies.put(cell, extractReferences(formula));
        }

        // Clean up references that no longer exist
        dependencies.values().forEach(set -> set.removeIf(ref -> !dependencies.containsKey(ref)));
    }



    private Set<String> extractReferences(String formula) {
        Set<String> refs = new HashSet<>();
        if (formula.startsWith("=")) {
            String[] tokens = formula.substring(1).split("[+\\-*/><=()]"); // Tokenize
            for (String token : tokens) {
                if (token.matches("^[A-Z]+[0-9]+$")) { // If it's a reference like A1, B2
                    refs.add(token);
                }
            }
        }
        return refs;
    }

    private boolean detectCycle(String cell, Set<String> visited) {
        if (visited.contains(cell)) return true; // Cycle detected
        visited.add(cell);

        Set<String> refs = dependencies.getOrDefault(cell, Collections.emptySet());
        for (String ref : refs) {
            if (detectCycle(ref, visited)) {
                return true;
            }
        }

        visited.remove(cell);
        return false;
    }

    public boolean hasCircularReference(String cellReference) {
        return detectCycle(cellReference, new HashSet<>());
    }

    String convertToReference(int row, int col) {
        StringBuilder colName = new StringBuilder();
        col++;

        while (col > 0) {
            col--;
            colName.insert(0, (char) ('A' + col % 26));
            col /= 26;
        }

        return colName.toString() + (row + 1);

    }

    public String getSelectedCellReference() {
        SpreadsheetCellModel selected = selectedCell.get();
        if (selected == null) return null;

        int row = selected.getRow();
        int col = selected.getCol();
        return convertToReference(row, col);
    }


}
