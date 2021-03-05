package gui.java.presentation.inputController;

import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;
import business.enums.TipoGenero;
import business.enums.FilterFieldType;
import business.enums.OperationType;
import gui.java.presentation.model.FilterConsultaModel;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterConsultaController extends BaseController implements Initializable {

    @FXML private ComboBox<String> selectedFilterFieldComboBox;

    @FXML private AnchorPane singleValueArea;
    @FXML private ComboBox<String> operationComboBox;
    @FXML private TextField valueField;
    @FXML private DatePicker valueDateField;
    @FXML private ComboBox<String> valueComboBoxField;

    @FXML private AnchorPane intervalValueArea;
    @FXML private TextField minValueField;
    @FXML private DatePicker minValueDateField;
    @FXML private TextField maxValueField;
    @FXML private DatePicker maxValueDateField;

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

        // Only allow for numbers
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        valueField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));
        minValueField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));
        maxValueField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));

        singleValueArea.setVisible(false);
        intervalValueArea.setVisible(false);
    }

    public void setModel(FilterConsultaModel model) {
        this.filterConsultaModel = model;

        selectedFilterFieldComboBox.valueProperty().bindBidirectional(filterConsultaModel.selectedFilterProperty());
        selectedFilterFieldComboBox.getSelectionModel().selectFirst();

        operationComboBox.valueProperty().bindBidirectional(filterConsultaModel.operationProperty());
        valueField.textProperty().bindBidirectional(filterConsultaModel.numberValueProperty());
        valueDateField.getEditor().textProperty().bindBidirectional(filterConsultaModel.dateValueProperty());
        valueComboBoxField.valueProperty().bindBidirectional(filterConsultaModel.selectionValueProperty());

        minValueField.textProperty().bindBidirectional(filterConsultaModel.minValueProperty());
        minValueDateField.getEditor().textProperty().bindBidirectional(filterConsultaModel.dateMinValueProperty());
        maxValueField.textProperty().bindBidirectional(filterConsultaModel.maxValueProperty());
        maxValueDateField.getEditor().textProperty().bindBidirectional(filterConsultaModel.dateMaxValueProperty());
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void selectedFilterFieldChange() {
        if(filterConsultaModel.getSelectedFilter().equals(FilterFieldType.NO_FILTER.toString())) {
            singleValueArea.setVisible(false);
            intervalValueArea.setVisible(false);

            okFilterBtn.disableProperty().unbind();
            okFilterBtn.setDisable(false);
        }
        else {
            if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.PROCESSO.toString()) ||
                    filterConsultaModel.getSelectedFilter().equals(FilterFieldType.IDADE.toString())) {

                singleValueArea.setVisible(true);
                intervalValueArea.setVisible(true);

                List<String> tiposOperacaoField = Stream.of(OperationType.values())
                        .map(OperationType::toString)
                        .collect(Collectors.toList());
                operationComboBox.getItems().setAll(tiposOperacaoField);
                operationComboBox.getSelectionModel().selectFirst();

                operationComboBox.setDisable(false);

                valueField.setVisible(true);
                valueDateField.setVisible(false);
                valueComboBoxField.setVisible(false);
                minValueField.setVisible(true);
                minValueDateField.setVisible(false);
                maxValueField.setVisible(true);
                maxValueDateField.setVisible(false);

                okFilterBtn.disableProperty().unbind();
                BooleanBinding booleanBind = valueField.textProperty().isEmpty()
                        .and(
                                minValueField.textProperty().isEmpty()
                                .or(maxValueField.textProperty().isEmpty())
                        );

                okFilterBtn.disableProperty().bind(booleanBind);
            }
            else {
                List<String> tiposOperacaoField = new ArrayList<>();
                tiposOperacaoField.add(OperationType.EQUALS.toString());
                tiposOperacaoField.add(OperationType.DIFFERENT.toString());

                if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.DATA.toString())){
                    tiposOperacaoField.add(OperationType.BETWEEN.toString());

                    singleValueArea.setVisible(true);
                    intervalValueArea.setVisible(true);

                    valueField.setVisible(false);
                    valueDateField.setVisible(true);
                    valueComboBoxField.setVisible(false);
                    minValueField.setVisible(false);
                    minValueDateField.setVisible(true);
                    maxValueField.setVisible(false);
                    maxValueDateField.setVisible(true);

                    okFilterBtn.disableProperty().unbind();
                    BooleanBinding booleanBind = valueDateField.getEditor().textProperty().isEmpty()
                            .and(
                                    minValueDateField.getEditor().textProperty().isEmpty()
                                    .or(maxValueDateField.getEditor().textProperty().isEmpty())
                            );

                    okFilterBtn.disableProperty().bind(booleanBind);
                }
                else {
                    singleValueArea.setVisible(true);
                    intervalValueArea.setVisible(false);

                    valueField.setVisible(false);
                    valueDateField.setVisible(false);
                    valueComboBoxField.setVisible(true);
                    minValueField.setVisible(false);
                    minValueDateField.setVisible(false);
                    maxValueField.setVisible(false);
                    maxValueDateField.setVisible(false);

                    okFilterBtn.disableProperty().unbind();
                    okFilterBtn.setDisable(false);
                }
                operationComboBox.getItems().setAll(tiposOperacaoField);
                operationComboBox.getSelectionModel().selectFirst();

                operationComboBox.setDisable(false);

                List<String> valuesField = new ArrayList<>();

                if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.TIPO.toString())){
                    valuesField = Stream.of(TipoConsulta.values())
                            .map(TipoConsulta::toString)
                            .collect(Collectors.toList());
                }
                else if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.AUTONOMIA.toString())){
                    valuesField = Stream.of(TipoAutonomia.values())
                            .map(TipoAutonomia::toString)
                            .collect(Collectors.toList());
                }
                else if (filterConsultaModel.getSelectedFilter().equals(FilterFieldType.SEXO.toString())){
                    valuesField = Stream.of(TipoGenero.values())
                            .map(TipoGenero::toString)
                            .collect(Collectors.toList());
                }

                valueComboBoxField.getItems().setAll(valuesField);
                valueComboBoxField.getSelectionModel().selectFirst();
            }
        }

        valueField.textProperty().set("");
        valueDateField.getEditor().textProperty().set("");
        minValueField.textProperty().set("");
        minValueDateField.getEditor().textProperty().set("");
        maxValueField.textProperty().set("");
        maxValueDateField.getEditor().textProperty().set("");
    }

    @FXML
    private void selectedOperationFieldChange() {
        if(filterConsultaModel.getOperation().equals(OperationType.BETWEEN.toString())) {
            valueField.textProperty().set("");
            valueField.setDisable(true);
            valueDateField.getEditor().textProperty().set("");
            valueDateField.setDisable(true);
            valueDateField.setDisable(true);
            valueComboBoxField.setDisable(true);
            minValueField.setDisable(false);
            minValueDateField.setDisable(false);
            maxValueField.setDisable(false);
            maxValueDateField.setDisable(false);
        }
        else {
            valueField.setDisable(false);
            valueDateField.setDisable(false);
            valueComboBoxField.setDisable(false);

            minValueField.textProperty().set("");
            minValueField.setDisable(true);
            minValueDateField.getEditor().textProperty().set("");
            minValueDateField.setDisable(true);

            maxValueField.textProperty().set("");
            maxValueField.setDisable(true);
            maxValueDateField.getEditor().textProperty().set("");
            maxValueDateField.setDisable(true);
        }
    }

    @FXML
    private void cancelFilter() {
        closeWindow();
    }

    @FXML
    private void filterConsulta(){
        FilterFieldType filterType = FilterFieldType.valueOf(filterConsultaModel.getSelectedFilter());
        OperationType operation = null;
        boolean filterSuccess = false;
        switch (filterType){
            case PROCESSO:
            case IDADE:
                int numberValue = 0;
                int numberMin = 0;
                int numberMax = 0;
                if(filterConsultaModel.getOperation().equals(OperationType.BETWEEN.toString())) {
                    numberMin = Integer.parseInt(filterConsultaModel.getMinValue());
                    numberMax = Integer.parseInt(filterConsultaModel.getMaxValue());

                    if (numberMin >= numberMax) {
                        showError(i18nBundle.getString("application.error.filter.min.max.ints"));
                        break;
                    }
                }
                else {
                    numberValue = Integer.parseInt(filterConsultaModel.getNumberValue());
                }

                operation = OperationType.valueOf(filterConsultaModel.getOperation());
                filterSuccess = mainWindowController.filterByNumberValues(filterType, operation, numberValue,
                        numberMin, numberMax);
                break;

            case TIPO:
            case AUTONOMIA:
            case SEXO:
                operation = OperationType.valueOf(filterConsultaModel.getOperation());
                String selectedValue = filterConsultaModel.getSelectionValue();
                filterSuccess = mainWindowController.filterByPresetValues(filterType, operation, selectedValue);
                break;

            case DATA:
                try {
                    //these dates are in the format yyyy-mm-dd
                    String dateValue = "";
                    String dateMin = "";
                    String dateMax = "";
                    if (filterConsultaModel.getOperation().equals(OperationType.BETWEEN.toString())) {
                        dateMin = filterConsultaModel.getDateMinValueFormated();
                        dateMax = filterConsultaModel.getDateMaxValueFormated();

                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dateMinParsed = format.parse(dateMin);
                        Date dateMaxParsed = format.parse(dateMax);

                        if (!dateMinParsed.before(dateMaxParsed)) {
                            showError(i18nBundle.getString("application.error.filter.min.max.dates"));
                            break;
                        }
                    }
                    else {
                        dateValue = filterConsultaModel.getDateValueFormated();

                        //test if the value is valid before sending the operation
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        format.parse(dateValue);
                    }

                    operation = OperationType.valueOf(filterConsultaModel.getOperation());
                    filterSuccess = mainWindowController.filterByDate(operation, dateValue, dateMin, dateMax);
                }
                catch (ParseException e) {
                    showError(i18nBundle.getString("application.error.check.inputs"));
                }
                break;

            default:
                //does the NO_FILTER and anything else if there is an error
                filterSuccess = mainWindowController.startGetAll();
        }

        if (filterSuccess) {
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) okFilterBtn.getScene().getWindow();
        stage.close();
    }
}
