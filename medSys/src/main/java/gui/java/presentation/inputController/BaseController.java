package gui.java.presentation.inputController;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class BaseController {
    ResourceBundle i18nBundle;

    public void showError(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(i18nBundle.getString("error.dialog.title"));
        alert.setHeaderText(null);
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(i18nBundle.getString("info.dialog.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showConfirmationNewDB(String message, MainWindowController mainWindowController) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(i18nBundle.getString("warning.dialog.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().toLowerCase().equals("ok")) {
                mainWindowController.restartDB();
            }
        });
    }

    public void setI18NBundle(ResourceBundle i18nBundle) {
        this.i18nBundle = i18nBundle;
    }

    public void saveToExternalFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            //todo
        }
    }

    public void openExternalFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import");

        fileChooser.showOpenDialog(stage);

        //todo
    }
}
