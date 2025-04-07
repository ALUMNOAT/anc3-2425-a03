package excel.view;

import excel.viewmodel.SpreadsheetViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuView extends HBox {
    private final SpreadsheetViewModel viewModel;

    public MenuView(SpreadsheetViewModel viewModel) {
        this.viewModel = viewModel;
        manageComponents();
        this.setPadding(new Insets(10));

        this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            //pour être sûr que la scene soit bien initialisée
            if (newScene != null) {
                // si une touche est appuyée
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    // si ctrl
                    if (event.isControlDown()) {
                        // si ctrl+z
                        if (event.getCode() == KeyCode.Z) {
                            viewModel.undo();
                            event.consume();
                            //si ctrl+y
                        } else if (event.getCode() == KeyCode.Y) {
                            viewModel.redo();
                            event.consume();
                        }
                    }
                });
            }
        });
    }

    private void manageComponents() {
        // Open et save
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        fileMenu.getItems().addAll(openItem, saveItem);

        // Undo et redo
        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        editMenu.getItems().addAll(undoItem, redoItem);

        MenuBar menuBar = new MenuBar(fileMenu, editMenu);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        this.getChildren().add(menuBar);

        // Actions des menus
        openItem.setOnAction(event -> open());
        saveItem.setOnAction(event -> save());
        undoItem.setOnAction(event -> viewModel.undo());
        redoItem.setOnAction(event -> viewModel.redo());
        redoItem.disableProperty().bind(viewModel.canRedo().not());
        undoItem.disableProperty().bind(viewModel.canUndo().not());
    }

    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            System.out.println("Fichier sélectionné : " + file.getAbsolutePath());
        }
    }

    private void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder un fichier");
        fileChooser.setInitialFileName("nouveau_fichier.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            System.out.println("Fichier enregistré : " + file.getAbsolutePath());
        }
    }
}
