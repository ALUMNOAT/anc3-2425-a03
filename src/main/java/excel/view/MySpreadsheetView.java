package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TablePosition;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.util.Objects;

public class MySpreadsheetView extends SpreadsheetView {
    private static final int CELL_PREF_WIDTH = 150;
    private final SpreadsheetViewModel viewModel;
    private final GridBase grid;

    public MySpreadsheetView(SpreadsheetViewModel viewModel) {
        this.viewModel = viewModel;
        this.setPadding(new Insets(10));
        this.grid = createGridAndBindings();
        this.setGrid(this.grid);
        configEditLogic();
        layoutSpreadSheet();
    }


    private void layoutSpreadSheet() {
        for (int column = 0; column < grid.getColumnCount(); column++) {
            this.getColumns().get(column).setPrefWidth(CELL_PREF_WIDTH);
        }
    }

    private void configEditLogic(){
        // Écouteur de la cellule en edition
        this.editingCellProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal != null) {
                var tablePosition = this.getSelectionModel().getSelectedCells().get(0);
                int row = tablePosition.getRow();
                int col = tablePosition.getColumn();

                SpreadsheetCell spreadsheetCell = grid.getRows().get(row).get(col);
                spreadsheetCell.itemProperty().set(viewModel.getCellValue(row, col));
            } else { // permet de refresh les différentes cases (repasser en result ou pour les références en cas de changement)
                int row = viewModel.getSelectedCellRow();
                int col = viewModel.getSelectedCellColumn();
                SpreadsheetCell spreadsheetCell = grid.getRows().get(row).get(col);
                spreadsheetCell.itemProperty().set(viewModel.getCellResult(row,col));
            }
        });


        // Écouteur de la cellule selectionnée
        this.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> { // ajout d'un écouteur obsverant si il y a un changement de cellule selectionnée
            while (change.next()) {
                if (!this.getSelectionModel().getSelectedCells().isEmpty()) {
                    var tablePosition = change.getAddedSubList().get(0);
                    viewModel.setSelectedCell(tablePosition.getRow(), tablePosition.getColumn());
                }
            }
        });
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

                // pour les refs (change que leur result et non leur value donc nécessaire)
                this.editableProperty().bind(viewModel.editableProperty());

                // pour les refs (change que leur result et non leur value donc nécessaire)
                viewModel.getCellResultProperty(finalRow, finalColumn).addListener((observable, oldVal, newVal) -> {
                    if (!Objects.equals(oldVal, newVal)) {
                        cell.itemProperty().set(newVal);
                    }
                });
                // Synchronisation entre le modèle et la vue
                cell.itemProperty().addListener((observableValue, oldVal, newVal) -> {
                    if (!Objects.equals(oldVal, newVal) && viewModel.getCellResult(finalRow,finalColumn)!= newVal && viewModel.getCellValue(finalRow,finalColumn) != newVal) { // si la valeur a changé et que le résultat est différent de la nouvelle valeur (pour éviter double passage)
                        viewModel.setCellResult(finalRow,finalColumn, (String) newVal);

                        viewModel.setCellValue(finalRow, finalColumn, (String) newVal);
                        cell.itemProperty().set(viewModel.getCellResult(finalRow,finalColumn));

                    }
                });

                viewModel.getCellValueProperty(finalRow, finalColumn).addListener((observableValue, oldVal, newVal) -> {
                    if (!Objects.equals(oldVal, newVal)) {
                        cell.itemProperty().set(viewModel.getCellResult(finalRow,finalColumn));
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
