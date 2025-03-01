package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class HeaderView  extends HBox {
    private final SpreadsheetViewModel viewModel;
    private final StringProperty selectedCellValue = new SimpleStringProperty(""); //valeur de la cellule selectionnée

    public HeaderView(SpreadsheetViewModel viewModel){
        this.viewModel = viewModel;

        manageComponents();

        this.setPadding(new Insets(10));



    }

    private void manageComponents() {
        TextField operationField = new TextField("");

        this.getChildren().add(operationField);
        HBox.setHgrow(operationField, Priority.ALWAYS);

        //lien avec cellule selectionnée
        operationField.textProperty().bindBidirectional(selectedCellValue);

        // change le texte en changeant de cellule
        viewModel.selectedCellProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedCellValue.set(newVal.getValue());
            } else {
                selectedCellValue.set("");
            }
        });

        // mis à jour de la cellule si changement de cellule selectionnée
        operationField.setOnAction(event -> {
            if (viewModel.selectedCellProperty().get() != null) {
                viewModel.selectedCellProperty().get().setResult(selectedCellValue.get());
                viewModel.selectedCellProperty().get().setValue(selectedCellValue.get());
            }
        });
    }
}
