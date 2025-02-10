package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HeaderView  extends HBox {
    private final SpreadsheetViewModel viewModel;

    public HeaderView(SpreadsheetViewModel viewModel){
        this.viewModel = viewModel;

        manageComponents();

        this.setPadding(new Insets(10));



    }

    private void manageComponents() {
        TextField OperationField = new TextField("");

        this.getChildren().add(OperationField);
        HBox.setHgrow(OperationField, Priority.ALWAYS);





    }
}
