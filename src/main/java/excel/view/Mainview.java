package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.scene.layout.BorderPane;

public class Mainview extends BorderPane {

    public Mainview(SpreadsheetViewModel viewModel) {
        this.setTop(new MenuView(viewModel));
        this.setCenter(new HeaderView(viewModel));
        this.setBottom(new MySpreadsheetView(viewModel));
    }
}
