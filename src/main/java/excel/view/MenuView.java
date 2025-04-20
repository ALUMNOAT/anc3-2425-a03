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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        MenuItem clearItem = new MenuItem("Clear All");
        editMenu.getItems().addAll(undoItem, redoItem, clearItem);

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
        clearItem.setOnAction(event -> viewModel.clearAllCells());
    }

    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier e4e (*.e4e)", "*.e4e"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            viewModel.open(file);
            System.out.println("Fichier ouvert : " + file.getAbsolutePath());
        }
    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder un fichier .e4e");

        // Filtre pour n'afficher que les fichiers .e4e
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichier e4e (*.e4e)", "*.e4e")
        );

        // Nom de fichier par défaut
        fileChooser.setInitialFileName("mon_fichier.e4e");

        File fichier = fileChooser.showSaveDialog(new Stage());

        if (fichier != null) {
            // S'assurer que le fichier a bien l'extension .e4e
            if (!fichier.getName().toLowerCase().endsWith(".e4e")) {
                fichier = new File(fichier.getAbsolutePath() + ".e4e");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                int rows = viewModel.getRowCount();
                int cols = viewModel.getColumnCount();

                // Sauvegarde des dimensions en haut du fichier
                writer.write(rows + "," + cols);
                writer.newLine();

                // Boucle sur toutes les cellules
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        String content = viewModel.getCellValue(row, col);
                        if (!content.isEmpty()) {
                            writer.write(row + "," + col + ";" + content);
                            writer.newLine();
                        }
                    }
                }

                System.out.println("Fichier sauvegardé : " + fichier.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
