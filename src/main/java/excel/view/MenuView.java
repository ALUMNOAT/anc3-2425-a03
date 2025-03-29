package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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


        // Ajout de l'écouteur d'événements pour l'élément de menu "Open"
        openItem.setOnAction(event -> open());

        // Ajout de l'écouteur d'événements pour l'élément de menu "Save"
        saveItem.setOnAction(event -> save());

        undoItem.setOnAction(event -> undo());

        redoItem.setOnAction(event -> redo());

        // disable le bouton redo/undo s'il n'est pas possible de l'utiliser
        redoItem.disableProperty().bind(viewModel.canRedo().not());
        undoItem.disableProperty().bind(viewModel.canUndo().not());
    }

    private void undo(){
        viewModel.undo();
    }

    private void redo(){
        viewModel.redo();
    }

    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            String absolutePath = file.getAbsolutePath();
            System.out.println("Fichier sélectionné : " + file.getAbsolutePath());
        }
    }

    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder un fichier");

        // Définir un nom par défaut et une extension
        fileChooser.setInitialFileName("nouveau_fichier.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            System.out.println("Fichier enregistré : " + file.getAbsolutePath());
        }
    }

}
