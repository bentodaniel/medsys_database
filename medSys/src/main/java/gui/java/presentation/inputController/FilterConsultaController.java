package gui.java.presentation.inputController;

import gui.java.presentation.enums.FilterFieldType;
import gui.java.presentation.model.FilterConsultaModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterConsultaController extends BaseController implements Initializable {

    @FXML private ComboBox<String> selectedFilterFieldComboBox;
    @FXML private ComboBox<String> operationComboBox;
    @FXML private TextField valueField;
    @FXML private TextField maxValueField;
    @FXML private TextField minValueField;
    @FXML private Button okFilterBtn;

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

        selectedFilterFieldComboBox.valueProperty().bindBidirectional(filterConsultaModel.selectedFilterProperty());
        operationComboBox.valueProperty().bindBidirectional(filterConsultaModel.operationProperty());
        valueField.textProperty().bindBidirectional(filterConsultaModel.valueProperty());
        maxValueField.textProperty().bindBidirectional(filterConsultaModel.maxValueProperty());
        minValueField.textProperty().bindBidirectional(filterConsultaModel.minValueProperty());
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void selectedFilterFieldChange() {

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
