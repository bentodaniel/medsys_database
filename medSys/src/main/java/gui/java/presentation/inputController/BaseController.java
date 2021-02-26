package gui.java.presentation.inputController;

import facade.DTO.ConsultaDTO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
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

    public void saveToExternalFile(Stage stage, MainWindowController mainWindowController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                List<ConsultaDTO> consultaDTOList = mainWindowController.getConsultaData();
                PrintWriter writer = new PrintWriter(file, "UTF-8");

                for (ConsultaDTO consulta : consultaDTOList) {
                    writer.println(
                            consulta.getProcesso() + "|" +
                            consulta.getTipo() + "|" +
                            consulta.getAutonomia() + "|" +
                            consulta.getSexo() + "|" +
                            consulta.getIdade() + "|" +
                            consulta.getProfissao() + "|" +
                            consulta.getMotivo() + "|" +
                            consulta.getProblemas() + "|" +
                            consulta.getMcdts() + "|" +
                            consulta.getReferenciacao() + "|" +
                            consulta.getGestos() + "|" +
                            consulta.getObservacoes() + "|" +
                            consulta.getData()
                    );
                }

                writer.close();
            }
            catch (Exception e) {
                showError(i18nBundle.getString("application.cant.write.save"));
            }

        }
    }

    public void openExternalFile(Stage stage, MainWindowController mainWindowController) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                mainWindowController.restartDB();

                String line = br.readLine();

                while (line != null) {
                    String[] values = line.split("\\|");

                    if (values.length == 13) {
                        ConsultaDTO consultaDTO = new ConsultaDTO(Integer.parseInt(values[0]), values[1], values[2],
                                values[3], Integer.parseInt(values[4]), values[5], values[6], values[7], values[8],
                                values[9], values[10], values[11], values[12]);

                        mainWindowController.addConsulta(consultaDTO);
                    }
                    line = br.readLine();
                }
                mainWindowController.startGetAll();

                br.close();
            }
            catch (FileNotFoundException e) {
                showError(i18nBundle.getString("application.cant.open.file"));
            } catch (IOException e) {
                showError(i18nBundle.getString("application.error.parsing.file"));
            }
        }
    }
}
