package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LabelView extends HBox {
    private final SpreadsheetViewModel viewModel;
    private final Label label = new Label();

    public LabelView(SpreadsheetViewModel viewModel) {
        this.viewModel = viewModel;
        manageComponents();
        addListeners();
    }

    private void manageComponents() {
        this.getChildren().add(label);
        this.setPadding(new Insets(10));
        updateLabel();
    }

    private void addListeners() {
        int rows = viewModel.getRowCount();
        int cols = viewModel.getColumnCount();

        // Ajoute un listener sur chaque cellule
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                viewModel.getCellValueProperty(row, col).addListener((observable, oldValue, newValue) -> {
                    updateLabel();
                });
            }
        }
    }

    private void updateLabel() {
        int count = 0;
        int rows = viewModel.getRowCount();
        int cols = viewModel.getColumnCount();
        /*
         Compte les formules SUM ou ^
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String cellValue = viewModel.getCellValue(row, col);
                if (cellValue.contains("SUM") || cellValue.contains("^")) {
                    count++;
                }
            }
        }
        */
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String cellValue = viewModel.getCellValue(row, col);
                if (cellValue.startsWith("=")) { // Vérifie si c'est une formule
                    int sumCount = cellValue.split("SUM", -1).length - 1; // Compte les occurrences de "SUM"
                    int powerCount = cellValue.split("\\^", -1).length - 1; // Compte les occurrences de "^"
                    count += sumCount + powerCount;
                }
            }
        }

        // Met à jour le texte et le style du label
        //label.setText("Le tableau contient " + count + " formules SUM ou ^");
        //label.setStyle(count > 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        if (count > 0) {
            label.setText("Il ya " + count + " opérateurs SUM et ^");
            label.setStyle("-fx-text-fill: green;");
        } else {
            label.setText("Il ya opérateur SUM et ^");
            label.setStyle("-fx-text-fill: red;");
        }
    }
}
