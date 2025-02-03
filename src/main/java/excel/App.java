package excel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        int rowCount = 15;
        int columnCount = 10;

        // Create the grid and initialize rows
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCell cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "");
                list.add(cell);
            }
            rows.add(list);
        }
        grid.setRows(rows);

        // Create the SpreadsheetView
        SpreadsheetView spreadsheetView = new SpreadsheetView(grid);

        // Listener for cell selection (entry)
        spreadsheetView.editingCellProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int rowIndex = newValue.getRow();
                int columnIndex = newValue.getColumn();
                String columnLetter = getColumnLetter(columnIndex);

                // Get the current value of the cell being edited
                ObservableList<SpreadsheetCell> currentRowCells = grid.getRows().get(rowIndex);
                Object currentValue = currentRowCells.get(columnIndex).getItem();

                // Handle null values
                String currentValueString = (currentValue != null) ? currentValue.toString() : "empty";

                System.out.println("Entered cell: " + columnLetter + (rowIndex + 1) + " with value: " + currentValueString);
            }
        });

        // Listener for when editing is committed (exit)
        spreadsheetView.editingCellProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                int oldRowIndex = oldValue.getRow();
                int oldColumnIndex = oldValue.getColumn();
                ObservableList<SpreadsheetCell> oldRowCells = grid.getRows().get(oldRowIndex);
                Object oldCellValue = oldRowCells.get(oldColumnIndex).getItem();

                // Handle null values
                String oldValueString = (oldCellValue != null) ? oldCellValue.toString() : "empty";

                System.out.println("Exited cell: " + getColumnLetter(oldColumnIndex) + (oldRowIndex + 1) + " with value: " + oldValueString);
            }
        });

        // Set up the scene and stage
        Scene scene = new Scene(spreadsheetView, 633, 315);
        primaryStage.setTitle("Spreadsheet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to convert column index to letter (0 = A, 1 = B, ...)
    private String getColumnLetter(int columnIndex) {
        return String.valueOf((char) ('A' + columnIndex));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
