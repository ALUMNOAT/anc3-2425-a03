package excel.view;

import excel.model.Builder;
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

        this.editingCellProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal != null) {
                System.out.println("edit cell");
                viewModel.toggleEditable();
            }
        });

        layoutSpreadSheet();

        // Gestion de la sélection de cellule
        this.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> { // ajout d'un écouteur obsverant si il y a un changement de cellule selectionnée
            while (change.next()) {
                viewModel.toggleEditable();
                if (!this.getSelectionModel().getSelectedCells().isEmpty()) { //vérifie si il y a bien une cellule sélectionné
                    TablePosition<?, ?> cell = change.getAddedSubList().get(0);  // On récupère la cellule sélectionnée
                    viewModel.setSelectedCell(cell.getRow(), cell.getColumn());
                }
            }
        });




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

                // Synchronisation entre le modèle et la vue
                cell.itemProperty().addListener((observableValue, oldVal, newVal) -> {
// DOIT ETRE MODIFIE POUR ETRE MIS DANS MODEL ET VIEWMODEL ET CHANGER LE CALCUL PAR UNE FONCTION AU LIEU DE TOUT CELA
                    if (!Objects.equals(oldVal, newVal)) { // si la valeur a changé

                            if (editableProperty().get()){
                                String context = (String) newVal;
                                Builder interpreter = new Builder(context);

                                // Interpret the expression
                                if (newVal != null){
                                    int result = interpreter.interpret((String) newVal);
                                    String res = Integer.toString(result);
                                    System.out.println("Result: " + result);
                                    cell.itemProperty().set(res);
                                }

                            }// si la valeur a changé
                            else {
                                cell.itemProperty().set(newVal);}

                        viewModel.setCellValue(finalRow, finalColumn, (String) newVal);
                    }
                    // A SUPPRIMER LE DEBUG PAR LA SUITE
                    System.out.println("DEBUG : change view " + newVal);
                    // viewModel.addAction("DEBUG : change view " + newVal); // TODO : remove
                });

                viewModel.getCellValueProperty(finalRow, finalColumn).addListener((observableValue, oldVal, newVal) -> {
// DOIT ETRE MODIFIE POUR ETRE MIS DANS MODEL ET VIEWMODEL ET CHANGER LE CALCUL PAR UNE FONCTION AU LIEU DE TOUT CELA
                    if (!Objects.equals(oldVal, newVal)) {
                        if (editableProperty().get()){
                            String context = (String) newVal;
                            Builder interpreter = new Builder(context);

                            // Interpret the expression
                            if (newVal != null){
                            int result = interpreter.interpret(newVal);
                            String res = Integer.toString(result);
                                System.out.println("Result: " + result);
                                cell.itemProperty().set(res);
                            }

                        }// si la valeur a changé
                        else {
                        cell.itemProperty().set(newVal);}
                    }
                    // A SUPPRIMER LE DEBUG PAR LA SUITE
                    System.out.println("DEBUG : change model " + newVal);
                    // viewModel.addAction("DEBUG : change model " + newVal); // TODO : remove
                });

                list.add(cell);
            }
            rows.add(list);
        }
        grid.setRows(rows);
        return grid;
    }
}
