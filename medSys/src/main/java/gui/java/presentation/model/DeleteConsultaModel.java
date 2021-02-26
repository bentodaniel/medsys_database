package gui.java.presentation.model;

import javafx.beans.property.*;

public class DeleteConsultaModel {

    private final StringProperty processo;

    public DeleteConsultaModel() {
        this.processo = new SimpleStringProperty();
    }

    /** GETTER */

    public String getProcesso() {
        return processo.get();
    }

    /** SETTER */

    public void setProcesso(String processo) {
        this.processo.set(processo);
    }

    /** PROPERTY */

    public StringProperty processoProperty() {
        return processo;
    }
}
