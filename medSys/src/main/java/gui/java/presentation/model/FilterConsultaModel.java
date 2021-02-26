package gui.java.presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilterConsultaModel {

    private final StringProperty processo;

    public FilterConsultaModel() {
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
