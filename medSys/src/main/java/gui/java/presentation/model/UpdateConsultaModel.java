package gui.java.presentation.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UpdateConsultaModel {
    private final StringProperty processoToUpdate;
    private final StringProperty tipo;
    private final StringProperty autonomia;
    private final StringProperty sexo;
    private final IntegerProperty idade;
    private final StringProperty profissao;
    private final StringProperty motivo;
    private final StringProperty problemas;
    private final StringProperty mcdts;
    private final StringProperty referenciacao;
    private final StringProperty gestos;
    private final StringProperty observacoes;
    private final StringProperty data;

    public UpdateConsultaModel(){
        this.processoToUpdate = new SimpleStringProperty();
        this.tipo = new SimpleStringProperty();
        this.autonomia = new SimpleStringProperty();
        this.sexo = new SimpleStringProperty();
        this.idade = new SimpleIntegerProperty();
        this.profissao = new SimpleStringProperty();
        this.motivo = new SimpleStringProperty();
        this.problemas = new SimpleStringProperty();
        this.mcdts = new SimpleStringProperty();
        this.referenciacao = new SimpleStringProperty();
        this.gestos = new SimpleStringProperty();
        this.observacoes = new SimpleStringProperty();
        this.data = new SimpleStringProperty();
    }

    /** GETTERS */

    public String getProcessoToUpdate() {
        return processoToUpdate.get();
    }

    public String getTipo() {
        return tipo.get();
    }

    public String getAutonomia() {
        return autonomia.get();
    }

    public String getSexo() {
        return sexo.get();
    }

    public int getIdade() {
        return idade.get();
    }

    public String getProfissao() {
        return profissao.get();
    }

    public String getMotivo() {
        return motivo.get();
    }

    public String getProblemas() {
        return problemas.get();
    }

    public String getMcdts() {
        return mcdts.get();
    }

    public String getReferenciacao() {
        return referenciacao.get();
    }

    public String getGestos() {
        return gestos.get();
    }

    public String getObservacoes() {
        return observacoes.get();
    }

    public String getData() {
        return data.get();
    }

    public String getDataFormated() {
        //data is in format dd-mm-yyyy
        //parse to yyyy-mm-dd
        String[] values = data.get().split("-");
        return values[2] + "-" + values[1] + "-" + values[0];
    }

    /** SETTERS */

    public void setProcessoToUpdate(String processoToUpdate) {
        this.processoToUpdate.set(processoToUpdate);
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public void setAutonomia(String autonomia) {
        this.autonomia.set(autonomia);
    }

    public void setSexo(String sexo) {
        this.sexo.set(sexo);
    }

    public void setIdade(int idade) {
        this.idade.set(idade);
    }

    public void setProfissao(String profissao) {
        this.profissao.set(profissao);
    }

    public void setMotivo(String motivo) {
        this.motivo.set(motivo);
    }

    public void setProblemas(String problemas) {
        this.problemas.set(problemas);
    }

    public void setMcdts(String mcdts) {
        this.mcdts.set(mcdts);
    }

    public void setReferenciacao(String referenciacao) {
        this.referenciacao.set(referenciacao);
    }

    public void setGestos(String gestos) {
        this.gestos.set(gestos);
    }

    public void setObservacoes(String observacoes) {
        this.observacoes.set(observacoes);
    }

    public void setData(String data) {
        this.data.set(data);
    }

    /** PROPERTYS */

    public StringProperty processoToUpdateProperty() {
        return processoToUpdate;
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public StringProperty autonomiaProperty() {
        return autonomia;
    }

    public StringProperty sexoProperty() {
        return sexo;
    }

    public IntegerProperty idadeProperty() {
        return idade;
    }

    public StringProperty profissaoProperty() {
        return profissao;
    }

    public StringProperty motivoProperty() {
        return motivo;
    }

    public StringProperty problemasProperty() {
        return problemas;
    }

    public StringProperty mcdtsProperty() {
        return mcdts;
    }

    public StringProperty referenciacaoProperty() {
        return referenciacao;
    }

    public StringProperty gestosProperty() {
        return gestos;
    }

    public StringProperty observacoesProperty() {
        return observacoes;
    }

    public StringProperty dataProperty() {
        return data;
    }
}
