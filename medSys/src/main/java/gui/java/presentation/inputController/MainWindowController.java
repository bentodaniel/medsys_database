package gui.java.presentation.inputController;

import business.enums.FilterFieldType;
import business.enums.OperationType;
import gui.java.presentation.model.FilterConsultaModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import facade.exceptions.ApplicationException;
import gui.java.presentation.model.UpdateConsultaModel;
import facade.DTO.ConsultaDTO;
import facade.services.ConsultaOperationsService;
import gui.java.presentation.model.CriarConsultaModel;
import gui.java.presentation.model.DeleteConsultaModel;
import org.controlsfx.control.CheckComboBox;

public class MainWindowController extends BaseController implements Initializable {

    @FXML private CheckComboBox<String> selectCheckComboBox;
    @FXML private Button showUpdate_button;
    @FXML private Button showDelete_button;
    @FXML private Button showFilter_button;

    @FXML private Label counterText;

    @FXML private TableView<ConsultaDTO> consultaTable;
    @FXML private TableColumn<ConsultaDTO, Integer> processoColumn;
    @FXML private TableColumn<ConsultaDTO, String> tipoColumn;
    @FXML private TableColumn<ConsultaDTO, String> autonomiaColumn;
    @FXML private TableColumn<ConsultaDTO, String> sexoColumn;
    @FXML private TableColumn<ConsultaDTO, Integer> idadeColumn;
    @FXML private TableColumn<ConsultaDTO, String> profissaoColumn;
    @FXML private TableColumn<ConsultaDTO, String> motivoColumn;
    @FXML private TableColumn<ConsultaDTO, String> problemasColumn;
    @FXML private TableColumn<ConsultaDTO, String> mcdtsColumn;
    @FXML private TableColumn<ConsultaDTO, String> referenciacaoColumn;
    @FXML private TableColumn<ConsultaDTO, String> gestosColumn;
    @FXML private TableColumn<ConsultaDTO, String> observacoesColumn;
    @FXML private TableColumn<ConsultaDTO, String> dataColumn;

    private ConsultaOperationsService consultaOperationsService;
    private ObservableList<ConsultaDTO> consultaData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setI18NBundle(resourceBundle);

        consultaData = FXCollections.observableArrayList();

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("<Tudo>", "Processo", "Tipo", "Autonomia", "Sexo", "Idade", "Profissao", "Motivo",
                "Problemas", "MCDTs", "Referenciacao", "Gestos", "Observacoes", "Data");

        selectCheckComboBox.getItems().addAll(items);
        selectCheckComboBox.getCheckModel().checkAll();
        selectCheckComboBox.setMaxWidth(150);

        selectCheckComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasRemoved()){
                    //An element was removed
                    if (c.toString().contains("<Tudo>") && c.getFrom() != -1){
                        //remove everything else
                        for (int i = 1; i < items.size(); i++) {
                            selectCheckComboBox.getCheckModel().clearCheck(i);

                            toggleCollumn(selectCheckComboBox.getCheckModel().getItem(i), false);
                        }
                    }
                    else {
                        //if it wasnt "tudo" but it is checked
                        if (selectCheckComboBox.getCheckModel().isChecked(0)){
                            selectCheckComboBox.getCheckModel().clearCheck(0);
                        }
                        toggleCollumn(c.toString(), false);
                    }
                }
                else if (c.wasAdded()) {
                    if (c.toString().contains("<Tudo>")){
                        //select everything else
                        for (int i = 1; i < items.size(); i++) {
                            if (!selectCheckComboBox.getCheckModel().isChecked(i)) {
                                selectCheckComboBox.getCheckModel().check(i);

                                toggleCollumn(selectCheckComboBox.getCheckModel().getItem(i), true);
                            }
                        }
                    }
                    else {
                        //if everything except for all is checked...

                        toggleCollumn(c.toString(), true);

                        boolean allChecked = true;
                        for (int i = 1; i < items.size(); i++) {
                            if (!selectCheckComboBox.getCheckModel().isChecked(i)) {
                                allChecked = false;
                            }
                        }

                        if (allChecked) {
                            selectCheckComboBox.getCheckModel().check(0);
                        }
                    }
                }
            }
        });

        showUpdate_button.disableProperty().bind(Bindings.size(consultaData).greaterThan(0).not());
        showDelete_button.disableProperty().bind(Bindings.size(consultaData).greaterThan(0).not());

        counterText.textProperty().bind(Bindings.size(consultaData).asString());

        this.processoColumn.setCellValueFactory(new PropertyValueFactory<>("processo"));
        this.tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        this.autonomiaColumn.setCellValueFactory(new PropertyValueFactory<>("autonomia"));
        this.sexoColumn.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        this.idadeColumn.setCellValueFactory(new PropertyValueFactory<>("idade"));
        this.profissaoColumn.setCellValueFactory(new PropertyValueFactory<>("profissao"));
        this.motivoColumn.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        this.problemasColumn.setCellValueFactory(new PropertyValueFactory<>("problemas"));
        this.mcdtsColumn.setCellValueFactory(new PropertyValueFactory<>("mcdts"));
        this.referenciacaoColumn.setCellValueFactory(new PropertyValueFactory<>("referenciacao"));
        this.gestosColumn.setCellValueFactory(new PropertyValueFactory<>("gestos"));
        this.observacoesColumn.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        this.dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    private void toggleCollumn(String content, boolean isVisible) {
        if (content.contains("Processo")){
            processoColumn.setVisible(isVisible);
        }
        else if (content.contains("Tipo")){
            tipoColumn.setVisible(isVisible);
        }
        else if (content.contains("Autonomia")){
            autonomiaColumn.setVisible(isVisible);
        }
        else if (content.contains("Sexo")){
            sexoColumn.setVisible(isVisible);
        }
        else if (content.contains("Idade")){
            idadeColumn.setVisible(isVisible);
        }
        else if (content.contains("Profissao")){
            profissaoColumn.setVisible(isVisible);
        }
        else if (content.contains("Motivo")){
            motivoColumn.setVisible(isVisible);
        }
        else if (content.contains("Problemas")){
            problemasColumn.setVisible(isVisible);
        }
        else if (content.contains("MCDTs")){
            mcdtsColumn.setVisible(isVisible);
        }
        else if (content.contains("Referenciacao")){
            referenciacaoColumn.setVisible(isVisible);
        }
        else if (content.contains("Gestos")){
            gestosColumn.setVisible(isVisible);
        }
        else if (content.contains("Observacoes")){
            observacoesColumn.setVisible(isVisible);
        }
        else if (content.contains("Data")){
            dataColumn.setVisible(isVisible);
        }
    }

    public void setService(ConsultaOperationsService service) {
        this.consultaOperationsService = service;
    }

    public ConsultaOperationsService getConsultaOperationsService() {
        return consultaOperationsService;
    }

    public boolean startGetAll() {
        return loadAllData();
    }

    @FXML
    private boolean loadAllData(){
        boolean result = false;
        try {
            this.consultaData.clear();
            this.consultaData.addAll(consultaOperationsService.getAllConsultas());
            result = true;
        }
        catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.getting.all.consultas") + ": " + e.getMessage());
        }
        consultaTable.getItems().setAll(consultaData);
        return result;
    }

    @FXML
    private void showNewWindow() {
        showConfirmationNewDB(i18nBundle.getString("application.new.db.text"), this);
    }

    public void restartDB() {
        try {
            boolean deleteResult = consultaOperationsService.removeAllConsultas();
            if (deleteResult) {
                consultaData.clear();
                consultaTable.getItems().clear();
            }
        } catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.removing.all.consultas") + ": " + e.getMessage());
        }
    }

    @FXML
    private void showExportWindow() {
        Stage stage = (Stage) consultaTable.getScene().getWindow();
        saveToExternalFile(stage, this);
    }

    @FXML
    private void showImportWindow() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(i18nBundle.getString("warning.dialog.title"));
        alert.setHeaderText(null);
        alert.setContentText(i18nBundle.getString("application.import.text"));
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().toLowerCase().equals("ok")) {
                Stage stage = (Stage) consultaTable.getScene().getWindow();
                openExternalFile(stage, this);
            }
        });
    }

    @FXML
    private void showCloseWindow() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(i18nBundle.getString("warning.dialog.title"));
        alert.setHeaderText(null);
        alert.setContentText(i18nBundle.getString("application.exit.text"));
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().toLowerCase().equals("ok")) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    @FXML
    private void showInfoWindow() {
        showInfo(i18nBundle.getString("application.info.text"));
    }

    @FXML
    private void showAddConsulta(){
        openNewWindow("/fxml/med_sys_add_consulta.fxml",
                i18nBundle.getString("application.add.new.title"), 1);
    }

    public boolean addConsulta(ConsultaDTO consultaDTO) {
        try {
            boolean addResult = consultaOperationsService.addConsulta(consultaDTO.getProcesso(), consultaDTO.getTipo(), consultaDTO.getAutonomia(),
                    consultaDTO.getSexo(), consultaDTO.getIdade(), consultaDTO.getProfissao(), consultaDTO.getMotivo(),
                    consultaDTO.getProblemas(), consultaDTO.getMcdts(), consultaDTO.getReferenciacao(), consultaDTO.getGestos(),
                    consultaDTO.getObservacoes(), consultaDTO.getData());

            if (addResult) {
                consultaData.add(consultaDTO);
                consultaTable.getItems().add(consultaDTO);
            }
            return addResult;
        }
        catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.adding.consulta") + ": " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void showUpdateConsulta(){
        openNewWindow("/fxml/med_sys_update_consulta.fxml",
                i18nBundle.getString("application.update.title"), 2);
    }

    public boolean updateConsulta(int id, ConsultaDTO newConsulta) {
        try {
            ConsultaDTO consultaDTO = consultaData.stream()
                    .filter(c -> c.getProcesso() == id)
                    .findAny()
                    .orElse(null);

            boolean updateResult = consultaOperationsService.updateConsulta(id, newConsulta.getProcesso(),
                    newConsulta.getTipo(), newConsulta.getAutonomia(), newConsulta.getSexo(), newConsulta.getIdade(),
                    newConsulta.getProfissao(), newConsulta.getMotivo(), newConsulta.getProblemas(),
                    newConsulta.getMcdts(), newConsulta.getReferenciacao(), newConsulta.getGestos(),
                    newConsulta.getObservacoes(), newConsulta.getData());
            if (updateResult) {
                consultaData.removeAll(consultaDTO);
                consultaTable.getItems().removeAll(consultaDTO);

                consultaData.add(newConsulta);
                consultaTable.getItems().add(newConsulta);
            }
            return updateResult;
        }
        catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.updating.consulta") + ": " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void showRemoveConsulta(){
        openNewWindow("/fxml/med_sys_delete_consulta.fxml",
                i18nBundle.getString("application.delete.title"), 3);
    }

    public boolean removeConsulta(int id) {
        try {
            ConsultaDTO consultaDTO = consultaData.stream()
                    .filter(c -> c.getProcesso() == id)
                    .findAny()
                    .orElse(null);

            boolean deleteResult = consultaOperationsService.removeConsulta(id);
            if (deleteResult) {
                consultaData.removeAll(consultaDTO);
                consultaTable.getItems().removeAll(consultaDTO);
            }
            return deleteResult;
        }
        catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.deleting.consulta") + ": " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void showFilterConsulta(){
        openNewWindow("/fxml/med_sys_filter_consulta.fxml",
                i18nBundle.getString("application.filter.title"), 4);
    }

    /**
     * Opens a new window
     * @param path The path of the fxml
     * @param title The title of the window
     * @param type The type of window: 1-> add; 2-> update; 3-> delete; 4-> filter
     */
    private void openNewWindow(String path, String title, int type) {
        if (type < 1 || type > 4){
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path), i18nBundle);
            Parent root = fxmlLoader.load();

            if (type == 1) {
                // ADD
                CriarConsultaController criarConsultaController = fxmlLoader.getController();
                CriarConsultaModel criarConsultaModel = new CriarConsultaModel();
                criarConsultaController.setModel(criarConsultaModel);
                criarConsultaController.setParentController(this);

            }
            else if (type == 2) {
                // UPDATE
                UpdateConsultaController updateConsultaController = fxmlLoader.getController();
                updateConsultaController.setParentController(this);
                UpdateConsultaModel updateConsultaModel = new UpdateConsultaModel();
                ConsultaDTO consultaDTO = consultaTable.getSelectionModel().getSelectedItem();
                updateConsultaController.setModel(updateConsultaModel,
                        consultaOperationsService.getAllProcessos(),
                        consultaDTO);
            }
            else if (type == 3) {
                // DELETE
                DeleteConsultaController deleteConsultaController = fxmlLoader.getController();
                deleteConsultaController.setParentController(this);
                DeleteConsultaModel deleteConsultaModel = new DeleteConsultaModel();
                ConsultaDTO consultaDTO = consultaTable.getSelectionModel().getSelectedItem();
                int selectedValue = consultaDTO == null ? -1 : consultaDTO.getProcesso();
                deleteConsultaController.setModel(deleteConsultaModel,
                        consultaOperationsService.getAllProcessos(),
                        selectedValue);
            }
            else {
                // FILTER
                FilterConsultaController filterConsultaController = fxmlLoader.getController();
                FilterConsultaModel filterConsultaModel = new FilterConsultaModel();
                filterConsultaController.setParentController(this);
                filterConsultaController.setModel(filterConsultaModel);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e){
            showError(i18nBundle.getString("error.general"));
        }
    }

    public List<ConsultaDTO> getConsultaData() {
        return consultaData;
    }

    /**
     * Filtra por processo ou por idade
     */
    public boolean filterByNumberValues(FilterFieldType filter, OperationType operation, int value, int min, int max) {
        boolean result = false;
        switch (filter) {
            case PROCESSO:
                try {
                    List<ConsultaDTO> filteredList = consultaOperationsService.filterByProcesso(
                            operation.toString(), value, min, max);

                    this.consultaData.clear();
                    this.consultaData.addAll(filteredList);
                    consultaTable.getItems().setAll(consultaData);
                    result = true;
                }
                catch (ApplicationException e) {
                    showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
                }
                break;

            case IDADE:
                try {
                    List<ConsultaDTO> filteredList = consultaOperationsService.filterByIdade(
                            operation.toString(), value, min, max);

                    this.consultaData.clear();
                    this.consultaData.addAll(filteredList);
                    consultaTable.getItems().setAll(consultaData);
                    result = true;
                }
                catch (ApplicationException e) {
                    showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
                }
                break;
        }
        return result;
    }

    /**
     * Filtra por valores predefinidos (enumerados)
     */
    public boolean filterByPresetValues(FilterFieldType filter, OperationType operation, String selectedValue) {
        boolean result = false;
        switch (filter) {
            case TIPO:
                try {
                    List<ConsultaDTO> filteredList = consultaOperationsService.filterByTipo(operation.toString(), selectedValue);

                    this.consultaData.clear();
                    this.consultaData.addAll(filteredList);
                    consultaTable.getItems().setAll(consultaData);
                    result = true;
                }
                catch (ApplicationException e) {
                    showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
                }
                break;

            case AUTONOMIA:
                try {
                    List<ConsultaDTO> filteredList = consultaOperationsService.filterByAutonomia(operation.toString(), selectedValue);

                    this.consultaData.clear();
                    this.consultaData.addAll(filteredList);
                    consultaTable.getItems().setAll(consultaData);
                    result = true;
                }
                catch (ApplicationException e) {
                    showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
                }
                break;

            case SEXO:
                try {
                    List<ConsultaDTO> filteredList = consultaOperationsService.filterByGenero(operation.toString(), selectedValue);

                    this.consultaData.clear();
                    this.consultaData.addAll(filteredList);
                    consultaTable.getItems().setAll(consultaData);
                    result = true;
                }
                catch (ApplicationException e) {
                    showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
                }
                break;
        }
        return result;
    }

    /**
     * Filtra por datas
     */
    public boolean filterByDate(OperationType operation, String dateValue, String dateMin, String dateMax) {
        try {
            List<ConsultaDTO> filteredList = consultaOperationsService.filterByData(operation.toString(),
                    dateValue, dateMin, dateMax);

            this.consultaData.clear();
            this.consultaData.addAll(filteredList);
            consultaTable.getItems().setAll(consultaData);
            return true;
        }
        catch (ApplicationException e) {
            showError(i18nBundle.getString("application.error.filtering.consultas") + ": " + e.getMessage());
        }
        return false;
    }
}
