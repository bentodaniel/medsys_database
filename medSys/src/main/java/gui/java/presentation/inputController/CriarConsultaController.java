package gui.java.presentation.inputController;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;
import business.enums.TipoGenero;
import facade.DTO.ConsultaDTO;
import gui.java.presentation.model.CriarConsultaModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CriarConsultaController extends BaseController implements Initializable {

    @FXML private TextField processoField;
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
    @FXML private Button criarConsultaBtn;

    private CriarConsultaModel criarConsultaModel;
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
        processoField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));
        idadeField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));


        // All needed fields should be filled
        BooleanBinding booleanBind = processoField.textProperty().isEmpty()
                .or(idadeField.textProperty().isEmpty())
                .or(motivoField.textProperty().isEmpty())
                .or(problemasField.textProperty().isEmpty())
                .or(mcdtsField.textProperty().isEmpty())
                .or(referenciacaoField.textProperty().isEmpty())
                .or(gestosField.textProperty().isEmpty())
                .or(observacoesField.textProperty().isEmpty())
                .or(dataDatePicker.getEditor().textProperty().isEmpty());

        criarConsultaBtn.disableProperty().bind(booleanBind);
    }

    public void setModel(CriarConsultaModel model) {
        this.criarConsultaModel = model;
        bindFields();
    }

    private void bindFields() {
        processoField.textProperty().bindBidirectional(criarConsultaModel.processoProperty(), new NumberStringConverter());

        tipoComboBox.valueProperty().bindBidirectional(criarConsultaModel.tipoProperty());
        tipoComboBox.getSelectionModel().selectFirst();
        autonomiaComboBox.valueProperty().bindBidirectional(criarConsultaModel.autonomiaProperty());
        autonomiaComboBox.getSelectionModel().selectFirst();
        sexoComboBox.valueProperty().bindBidirectional(criarConsultaModel.sexoProperty());
        sexoComboBox.getSelectionModel().selectFirst();

        idadeField.textProperty().bindBidirectional(criarConsultaModel.idadeProperty(), new NumberStringConverter());
        profissaoField.textProperty().bindBidirectional(criarConsultaModel.profissaoProperty());
        motivoField.textProperty().bindBidirectional(criarConsultaModel.motivoProperty());
        problemasField.textProperty().bindBidirectional(criarConsultaModel.problemasProperty());
        mcdtsField.textProperty().bindBidirectional(criarConsultaModel.mcdtsProperty());
        referenciacaoField.textProperty().bindBidirectional(criarConsultaModel.referenciacaoProperty());
        gestosField.textProperty().bindBidirectional(criarConsultaModel.gestosProperty());
        observacoesField.textProperty().bindBidirectional(criarConsultaModel.observacoesProperty());
        dataDatePicker.getEditor().textProperty().bindBidirectional(criarConsultaModel.dataProperty());
    }

    public void setParentController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    @FXML
    private void cancelCriar() {
        closeWindow();
    }

    @FXML
    private void criarConsulta(){
        ConsultaDTO consultaDTO = new ConsultaDTO(criarConsultaModel.getProcesso(), criarConsultaModel.getTipo(),
                criarConsultaModel.getAutonomia(), criarConsultaModel.getSexo(), criarConsultaModel.getIdade(),
                criarConsultaModel.getProfissao(), criarConsultaModel.getMotivo(), criarConsultaModel.getProblemas(),
                criarConsultaModel.getMcdts(), criarConsultaModel.getReferenciacao(), criarConsultaModel.getGestos(),
                criarConsultaModel.getObservacoes(), criarConsultaModel.getData());
        boolean addSuccess = mainWindowController.addConsulta(consultaDTO);

        if (addSuccess){
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) criarConsultaBtn.getScene().getWindow();
        stage.close();
    }
}
