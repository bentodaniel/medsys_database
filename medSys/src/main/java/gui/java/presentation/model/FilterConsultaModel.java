package gui.java.presentation.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilterConsultaModel {

    private final StringProperty selectedFilter;
    private final StringProperty operation;
    private final StringProperty numberValue;
    private final StringProperty dateValue;
    private final StringProperty selectionValue;
    private final StringProperty minValue;
    private final StringProperty dateMinValue;
    private final StringProperty maxValue;
    private final StringProperty dateMaxValue;

    public FilterConsultaModel() {
        this.selectedFilter = new SimpleStringProperty();
        this.operation = new SimpleStringProperty();
        this.numberValue = new SimpleStringProperty();
        this.dateValue = new SimpleStringProperty();
        this.selectionValue = new SimpleStringProperty();
        this.minValue = new SimpleStringProperty();
        this.dateMinValue = new SimpleStringProperty();
        this.maxValue = new SimpleStringProperty();
        this.dateMaxValue = new SimpleStringProperty();
    }

    /** GETTER */

    public String getSelectedFilter() {
        return selectedFilter.get();
    }

    public String getOperation() {
        return operation.get();
    }

    public String getNumberValue() {
        return numberValue.get();
    }

    public String getDateValue() {
        return dateValue.get();
    }

    public String getSelectionValue() {
        return selectionValue.get();
    }

    public String getMinValue() {
        return minValue.get();
    }

    public String getDateMinValue() {
        return dateMinValue.get();
    }

    public String getMaxValue() {
        return maxValue.get();
    }

    public String getDateMaxValue() {
        return dateMaxValue.get();
    }

    /** SETTER */

    public void setSelectedFilter(String selectedFilter) {
        this.selectedFilter.set(selectedFilter);
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public void setNumberValue(String value) {
        this.numberValue.set(value);
    }

    public void setDateValue(String dateValue) {
        this.dateValue.set(dateValue);
    }

    public void setSelectionValue(String selectionValue) {
        this.selectionValue.set(selectionValue);
    }

    public void setMinValue(String minValue) {
        this.minValue.set(minValue);
    }

    public void setDateMinValue(String dateMinValue) {
        this.dateMinValue.set(dateMinValue);
    }

    public void setMaxValue(String maxValue) {
        this.maxValue.set(maxValue);
    }

    public void setDateMaxValue(String dateMaxValue) {
        this.dateMaxValue.set(dateMaxValue);
    }

    /** PROPERTY */

    public StringProperty selectedFilterProperty() {
        return selectedFilter;
    }

    public StringProperty operationProperty() {
        return operation;
    }

    public StringProperty numberValueProperty() {
        return numberValue;
    }

    public StringProperty dateValueProperty() {
        return dateValue;
    }

    public StringProperty selectionValueProperty() {
        return selectionValue;
    }

    public StringProperty minValueProperty() {
        return minValue;
    }

    public StringProperty dateMinValueProperty() {
        return dateMinValue;
    }

    public StringProperty maxValueProperty() {
        return maxValue;
    }

    public StringProperty dateMaxValueProperty() {
        return dateMaxValue;
    }
}
