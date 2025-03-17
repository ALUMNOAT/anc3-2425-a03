package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MenuView extends HBox {
    private final SpreadsheetViewModel viewModel;

    public MenuView(SpreadsheetViewModel viewModel){

        this.viewModel = viewModel;

        manageComponents();

        this.setPadding(new Insets(10));



    }
    private void manageComponents(){
        Menu fileMenu  = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");

        fileMenu.getItems().add(openItem);
        fileMenu.getItems().add(saveItem);


        Menu EditMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");

        EditMenu.getItems().add(undoItem);
        EditMenu.getItems().add(redoItem);
        MenuBar menuBar = new MenuBar(fileMenu, EditMenu);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        this.getChildren().add(menuBar);
    }

}
