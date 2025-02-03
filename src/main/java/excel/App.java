package excel;

import excel.model.SpreadsheetController;
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

        // Attach the controller to handle cell events
        new SpreadsheetController(spreadsheetView, grid);

        // Set up the scene and stage
        Scene scene = new Scene(spreadsheetView, 633, 315);
        primaryStage.setTitle("Spreadsheet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
