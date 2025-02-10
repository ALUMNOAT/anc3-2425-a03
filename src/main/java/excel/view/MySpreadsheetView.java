package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.util.Objects;

public class MySpreadsheetView extends SpreadsheetView {
    private static final int CELL_PREF_WIDTH = 150;
    private final SpreadsheetViewModel viewModel;
    private final GridBase grid;

    public MySpreadsheetView(SpreadsheetViewModel viewModel){
        this.viewModel = viewModel;

        this.setPadding(new Insets(10));
        this.grid = createGridAndBindings();
        this.setGrid(this.grid);

        layoutSpreadSheet();

    }

    private void layoutSpreadSheet() {
        for (int column = 0; column < grid.getColumnCount(); column++) {
            this.getColumns().get(column).setPrefWidth(CELL_PREF_WIDTH);
        }
    }

    private GridBase createGridAndBindings() {
        GridBase grid = new GridBase(viewModel.getRowCount(), viewModel.getColumnCount());

        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCell cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "");

                int finalRow = row;
                int finalColumn = column;
                cell.itemProperty().addListener((observableValue, oldVal, newVal) -> {
                    if(!Objects.equals(oldVal, newVal)) {
                        viewModel.setCellValue(finalRow, finalColumn, (String) newVal);
// A SUPPRIMER LE DEBUG PAR LA SUITE
                        System.out.println("DEBUG : change view " + newVal);
                        // viewModel.addAction("DEBUG : change view " + newVal); // TODO : remove
                    }
                });

                viewModel.getCellValueProperty(finalRow, finalColumn).addListener((observableValue, oldVal, newVal) -> {
                    if(!Objects.equals(oldVal, newVal)) {
                        cell.itemProperty().set(newVal);
// A SUPPRIMER LE DEBUG PAR LA SUITE
                        System.out.println("DEBUG : change model " + newVal);
                        // viewModel.addAction("DEBUG : change model " + newVal); // TODO : remove
                    }
                });

                list.add(cell);
            }
            rows.add(list);
        }
        grid.setRows(rows);
        return grid;
    }
}
