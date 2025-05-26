package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.scene.layout.VBox;

public class Mainview extends VBox {
    public Mainview(SpreadsheetViewModel viewModel) {
        this.getChildren().add(new MenuView(viewModel));
        this.getChildren().add(new HeaderView(viewModel));
        this.getChildren().add(new MySpreadsheetView(viewModel));
        this.getChildren().add(new LabelView(viewModel));
    }
}
