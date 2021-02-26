package gui.java.presentation.inputController;

import business.enums.TipoConsulta;
import gui.java.presentation.enums.FilterFieldType;
import gui.java.presentation.model.DeleteConsultaModel;
import gui.java.presentation.model.FilterConsultaModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterConsultaController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> selectedFilterFieldComboBox;


    @FXML
    private Button okFilterBtn;

    private FilterConsultaModel filterConsultaModel;
    private MainWindowController mainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setI18NBundle(resources);

        List<String> tiposFilterField = Stream.of(FilterFieldType.values())
                .map(FilterFieldType::toString)
                .collect(Collectors.toList());
        selectedFilterFieldComboBox.getItems().setAll(tiposFilterField);
        selectedFilterFieldComboBox.getSelectionModel().selectFirst();

        
    }

    public void setModel(FilterConsultaModel model) {
        this.filterConsultaModel = model;

        /*
        int index = processosList.indexOf(selectedItem);

        List<String> processosIds = processosList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        selectedProcessComboBox.getItems().setAll(processosIds);

        selectedProcessComboBox.valueProperty().bindBidirectional(
                deleteConsultaModel.processoProperty());

        selectedProcessComboBox.getSelectionModel().select(index == -1 ? 0 : index);

         */
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void resetFilter() {

    }

    @FXML
    private void cancelFilter() {
        closeWindow();
    }

    @FXML
    private void filterConsulta(){
        //todo
        //mainWindowController.removeConsulta(Integer.parseInt(deleteConsultaModel.getProcesso()));
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) okFilterBtn.getScene().getWindow();
        stage.close();
    }
}
