package gui.java.presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilterConsultaModel {

    private final StringProperty selectedFilter;
    private final StringProperty operation;
    private final StringProperty value;
    private final StringProperty minValue;
    private final StringProperty maxValue;

    public FilterConsultaModel() {
        this.selectedFilter = new SimpleStringProperty();
        this.operation = new SimpleStringProperty();
        this.value = new SimpleStringProperty();
        this.minValue = new SimpleStringProperty();
        this.maxValue = new SimpleStringProperty();
    }

    /** GETTER */

    public String getSelectedFilter() {
        return selectedFilter.get();
    }

    public String getOperation() {
        return operation.get();
    }

    public String getValue() {
        return value.get();
    }

    public String getMinValue() {
        return minValue.get();
    }

    public String getMaxValue() {
        return maxValue.get();
    }

    /** SETTER */

    public void setSelectedFilter(String selectedFilter) {
        this.selectedFilter.set(selectedFilter);
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void setMinValue(String minValue) {
        this.minValue.set(minValue);
    }

    public void setMaxValue(String maxValue) {
        this.maxValue.set(maxValue);
    }

    /** PROPERTY */

    public StringProperty selectedFilterProperty() {
        return selectedFilter;
    }

    public StringProperty operationProperty() {
        return operation;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public StringProperty minValueProperty() {
        return minValue;
    }

    public StringProperty maxValueProperty() {
        return maxValue;
    }
}
