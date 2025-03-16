package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.scene.layout.BorderPane;

public class Mainview extends BorderPane {

    public Mainview(SpreadsheetViewModel viewModel) {
        this.setTop(new HeaderView(viewModel));
        this.setCenter(new MySpreadsheetView(viewModel));
    }
}
