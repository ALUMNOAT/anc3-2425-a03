package excel.model;

import excel.viewmodel.SpreadsheetCellViewModel;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.IntStream;


public class SpreadsheetModel {
    private final int rows;
    private final int cols;
    private final ObjectProperty<SpreadsheetCellModel> selectedCell = new SimpleObjectProperty<>();
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList(
            item -> new Observable[]{});

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
        int[] coords = parseCellReference(cellReference);
        if (coords == null) return null; // Return null for invalid references
        SpreadsheetCellModel cell = getCell(coords[0], coords[1]);
        String result = cell.getResult(); // Assuming getResult() returns the cell value

        if (result.equalsIgnoreCase("true")) return true;
        if (result.equalsIgnoreCase("false")) return false;

        try {
            return Double.parseDouble(result);
        } catch (NumberFormatException e) {
            return "#VALEUR"; // Error handling for invalid numbers
        }
    }


    private int[] parseCellReference(String reference) {
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



}
