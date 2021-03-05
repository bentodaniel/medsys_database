package gui.java.presentation.inputController;

import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;
import business.enums.TipoGenero;
import facade.DTO.ConsultaDTO;
import facade.exceptions.ApplicationException;
import facade.services.ConsultaOperationsService;
import gui.java.presentation.model.UpdateConsultaModel;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateConsultaController extends BaseController implements Initializable {

    @FXML private ComboBox<String> selectedProcessComboBox;
    @FXML private ComboBox<String> tipoComboBox;
    @FXML private ComboBox<String> autonomiaComboBox;
    @FXML private ComboBox<String> sexoComboBox;
    @FXML private TextField idadeField;
    @FXML private TextField profissaoField;
    @FXML private TextField motivoField;
    @FXML private TextField problemasField;
    @FXML private TextField mcdtsField;
    @FXML private TextField referenciacaoField;
    @FXML private TextField gestosField;
    @FXML private TextField observacoesField;
    @FXML private DatePicker dataDatePicker;
    @FXML private Button okUpdateBtn;

    private UpdateConsultaModel updateConsultaModel;
    private MainWindowController mainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setI18NBundle(resources);

        List<String> tiposConsulta = Stream.of(TipoConsulta.values())
                .map(TipoConsulta::toString)
                .collect(Collectors.toList());
        tipoComboBox.getItems().setAll(tiposConsulta);
        tipoComboBox.getSelectionModel().selectFirst();

        List<String> tiposAutonomia = Stream.of(TipoAutonomia.values())
                .map(TipoAutonomia::toString)
                .collect(Collectors.toList());
        autonomiaComboBox.getItems().setAll(tiposAutonomia);
        autonomiaComboBox.getSelectionModel().selectFirst();

        List<String> tiposGenero = Stream.of(TipoGenero.values())
                .map(TipoGenero::toString)
                .collect(Collectors.toList());
        sexoComboBox.getItems().setAll(tiposGenero);
        sexoComboBox.getSelectionModel().selectFirst();

        // Only allow for numbers
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        idadeField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));

        // Do not allow "|" character
        UnaryOperator<TextFormatter.Change> charFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[^|]*")) {
                return change;
            }
            return null;
        };
        profissaoField.setTextFormatter(new TextFormatter<>(charFilter));
        motivoField.setTextFormatter(new TextFormatter<>(charFilter));
        problemasField.setTextFormatter(new TextFormatter<>(charFilter));
        mcdtsField.setTextFormatter(new TextFormatter<>(charFilter));
        referenciacaoField.setTextFormatter(new TextFormatter<>(charFilter));
        gestosField.setTextFormatter(new TextFormatter<>(charFilter));
        observacoesField.setTextFormatter(new TextFormatter<>(charFilter));

        // All needed fields should be filled
        BooleanBinding booleanBind = idadeField.textProperty().isEmpty()
                .or(motivoField.textProperty().isEmpty())
                .or(problemasField.textProperty().isEmpty())
                .or(mcdtsField.textProperty().isEmpty())
                .or(referenciacaoField.textProperty().isEmpty())
                .or(gestosField.textProperty().isEmpty())
                .or(observacoesField.textProperty().isEmpty())
                .or(dataDatePicker.getEditor().textProperty().isEmpty());

        okUpdateBtn.disableProperty().bind(booleanBind);
    }

    public void setModel(UpdateConsultaModel model, List<Integer> processosList, ConsultaDTO consultaSelecionada) {
        this.updateConsultaModel = model;

        int selectedId = consultaSelecionada == null ? -1 : consultaSelecionada.getProcesso();
        int index = processosList.indexOf(selectedId);

        List<String> processosIds = processosList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        selectedProcessComboBox.getItems().setAll(processosIds);

        selectedProcessComboBox.valueProperty().bindBidirectional(updateConsultaModel.processoToUpdateProperty());

        selectedProcessComboBox.getSelectionModel().select(index == -1 ? 0 : index);

        bindFields();

        consultaSelecionada = getSelectedConsulta();

        if (consultaSelecionada != null) {
            fillFields(consultaSelecionada);
        }

    }

    private void bindFields() {
        tipoComboBox.valueProperty().bindBidirectional(updateConsultaModel.tipoProperty());
        tipoComboBox.getSelectionModel().selectFirst();
        autonomiaComboBox.valueProperty().bindBidirectional(updateConsultaModel.autonomiaProperty());
        autonomiaComboBox.getSelectionModel().selectFirst();
        sexoComboBox.valueProperty().bindBidirectional(updateConsultaModel.sexoProperty());
        sexoComboBox.getSelectionModel().selectFirst();

        idadeField.textProperty().bindBidirectional(updateConsultaModel.idadeProperty(), new NumberStringConverter());
        profissaoField.textProperty().bindBidirectional(updateConsultaModel.profissaoProperty());
        motivoField.textProperty().bindBidirectional(updateConsultaModel.motivoProperty());
        problemasField.textProperty().bindBidirectional(updateConsultaModel.problemasProperty());
        mcdtsField.textProperty().bindBidirectional(updateConsultaModel.mcdtsProperty());
        referenciacaoField.textProperty().bindBidirectional(updateConsultaModel.referenciacaoProperty());
        gestosField.textProperty().bindBidirectional(updateConsultaModel.gestosProperty());
        observacoesField.textProperty().bindBidirectional(updateConsultaModel.observacoesProperty());
        dataDatePicker.getEditor().textProperty().bindBidirectional(updateConsultaModel.dataProperty());
    }

    private void fillFields(ConsultaDTO consulta) {
        tipoComboBox.getSelectionModel().select(consulta.getTipo());
        autonomiaComboBox.getSelectionModel().select(consulta.getAutonomia());
        sexoComboBox.getSelectionModel().select(consulta.getSexo());
        idadeField.textProperty().set(String.valueOf(consulta.getIdade()));
        profissaoField.textProperty().set(consulta.getProfissao());
        motivoField.textProperty().set(consulta.getMotivo());
        problemasField.textProperty().set(consulta.getProblemas());
        mcdtsField.textProperty().set(consulta.getMcdts());
        referenciacaoField.textProperty().set(consulta.getReferenciacao());
        gestosField.textProperty().set(consulta.getGestos());
        observacoesField.textProperty().set(consulta.getObservacoes());
        dataDatePicker.getEditor().textProperty().set(consulta.getDataFormatDDMMYYYY());
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void updateSelectedProcesso() {
        ConsultaDTO selectedConsulta = getSelectedConsulta();
        if (selectedConsulta != null) {
            fillFields(selectedConsulta);
        }
    }

    private ConsultaDTO getSelectedConsulta() {
        int selectedProcesso = Integer.parseInt(selectedProcessComboBox.getSelectionModel().getSelectedItem());
        ConsultaOperationsService service = mainWindowController.getConsultaOperationsService();

        if (service != null) {
            try {
                return service.getConsulta(selectedProcesso);
            }
            catch (ApplicationException e) {
                showError(i18nBundle.getString("application.error.getting.selected.consulta") + ": " + e.getMessage());
            }
        }
        return null;
    }

    @FXML
    private void cancelUpdate() {
        closeWindow();
    }

    @FXML
    private void updateConsulta(){
        int processo = Integer.parseInt(updateConsultaModel.getProcessoToUpdate());
        ConsultaDTO consultaDTO = new ConsultaDTO(processo, updateConsultaModel.getTipo(),
                updateConsultaModel.getAutonomia(), updateConsultaModel.getSexo(), updateConsultaModel.getIdade(),
                updateConsultaModel.getProfissao(), updateConsultaModel.getMotivo(), updateConsultaModel.getProblemas(),
                updateConsultaModel.getMcdts(), updateConsultaModel.getReferenciacao(), updateConsultaModel.getGestos(),
                updateConsultaModel.getObservacoes(), updateConsultaModel.getDataFormated());
        boolean updateSuccess = mainWindowController.updateConsulta(processo, consultaDTO);

        if (updateSuccess) {
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) okUpdateBtn.getScene().getWindow();
        stage.close();
    }
}
