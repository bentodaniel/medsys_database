package gui.java.presentation.inputController;

import gui.java.presentation.enums.FilterFieldType;
import gui.java.presentation.enums.OperationType;
import gui.java.presentation.model.FilterConsultaModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterConsultaController extends BaseController implements Initializable {

    @FXML private ComboBox<String> selectedFilterFieldComboBox;
    @FXML private ComboBox<String> operationComboBox;
    @FXML private TextField valueField;
    @FXML private TextField minValueField;
    @FXML private TextField maxValueField;
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

        operationComboBox.setDisable(true);
        valueField.setDisable(true);
        minValueField.setDisable(true);
        maxValueField.setDisable(true);
    }

    public void setModel(FilterConsultaModel model) {
        this.filterConsultaModel = model;

        selectedFilterFieldComboBox.valueProperty().bindBidirectional(filterConsultaModel.selectedFilterProperty());
        selectedFilterFieldComboBox.getSelectionModel().selectFirst();

        operationComboBox.valueProperty().bindBidirectional(filterConsultaModel.operationProperty());
        valueField.textProperty().bindBidirectional(filterConsultaModel.valueProperty());
        minValueField.textProperty().bindBidirectional(filterConsultaModel.minValueProperty());
        maxValueField.textProperty().bindBidirectional(filterConsultaModel.maxValueProperty());

    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void selectedFilterFieldChange() {
        if(filterConsultaModel.getSelectedFilter().equals(FilterFieldType.NO_FILTER.toString())) {
            operationComboBox.setDisable(true);
            valueField.setDisable(true);
            minValueField.setDisable(true);
            maxValueField.setDisable(true);
        }
        else {
            if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.PROCESSO.toString()) ||
                    filterConsultaModel.getSelectedFilter().equals(FilterFieldType.IDADE.toString())) {

                List<String> tiposOperacaoField = Stream.of(OperationType.values())
                        .map(OperationType::toString)
                        .collect(Collectors.toList());
                operationComboBox.getItems().setAll(tiposOperacaoField);
                operationComboBox.getSelectionModel().selectFirst();

                operationComboBox.setDisable(false);
            }
            else {
                List<String> tiposOperacaoField = new ArrayList<>();
                tiposOperacaoField.add(OperationType.EQUALS.toString());
                tiposOperacaoField.add(OperationType.DIFFERENT.toString());

                if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.DATA.toString())){
                    tiposOperacaoField.add(OperationType.BETWEEN.toString());
                }
                operationComboBox.getItems().setAll(tiposOperacaoField);
                operationComboBox.getSelectionModel().selectFirst();

                operationComboBox.setDisable(false);
            }
        }
    }

    @FXML
    private void selectedOperationFieldChange() {
        if(filterConsultaModel.getOperation().equals(OperationType.BETWEEN.toString())) {
            valueField.textProperty().set("");
            valueField.setDisable(true);
            minValueField.setDisable(false);
            maxValueField.setDisable(false);
        }
        else {
            valueField.setDisable(false);
            minValueField.textProperty().set("");
            minValueField.setDisable(true);
            maxValueField.textProperty().set("");
            maxValueField.setDisable(true);
        }
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
