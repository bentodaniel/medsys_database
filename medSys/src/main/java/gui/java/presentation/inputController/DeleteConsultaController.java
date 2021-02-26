package gui.java.presentation.inputController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import gui.java.presentation.model.DeleteConsultaModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DeleteConsultaController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> selectedProcessComboBox;

    private DeleteConsultaModel deleteConsultaModel;
    private MainWindowController mainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setI18NBundle(resources);
    }

    public void setModel(DeleteConsultaModel model, List<Integer> processosList, int selectedItem) {
        this.deleteConsultaModel = model;

        int index = processosList.indexOf(selectedItem);

        List<String> processosIds = processosList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        selectedProcessComboBox.getItems().setAll(processosIds);

        selectedProcessComboBox.valueProperty().bindBidirectional(
                deleteConsultaModel.processoProperty());

        selectedProcessComboBox.getSelectionModel().select(index == -1 ? 0 : index);
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void cancelDelete() {
        closeWindow();
    }

    @FXML
    private void deleteConsulta(){
        boolean deleteSuccess = mainWindowController.removeConsulta(Integer.parseInt(deleteConsultaModel.getProcesso()));

        if (deleteSuccess) {
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) selectedProcessComboBox.getScene().getWindow();
        stage.close();
    }
}
