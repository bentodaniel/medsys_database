package facade.DTO;

import business.Consulta;

import java.text.SimpleDateFormat;

public class ConsultaDTO {

    private int processo;
    private String tipo;
    private String autonomia;
    private String sexo;
    private int idade;
    private String profissao;
    private String motivo;
    private String problemas;
    private String mcdts;
    private String referenciacao;
    private String gestos;
    private String observacoes;
    private String data;

    /**
     * CONSTRUCTORS
     */

    public ConsultaDTO() {
    }

    public ConsultaDTO(int processo, String tipo, String autonomia, String sexo,
                       int idade, String profissao, String motivo, String problemas,
                       String mcdts, String referenciacao, String gestos, String observacoes, String data) {
        this.processo = processo;
        this.tipo = tipo;
        this.autonomia = autonomia;
        this.sexo = sexo;
        this.idade = idade;
        this.profissao = profissao;
        this.motivo = motivo;
        this.problemas = problemas;
        this.mcdts = mcdts;
        this.referenciacao = referenciacao;
        this.gestos = gestos;
        this.observacoes = observacoes;
        this.data = data;
    }

    /** GETTERS */

    public int getProcesso() {
        return processo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAutonomia() {
        return autonomia;
    }

    public String getSexo() {
        return sexo;
    }

    public int getIdade() {
        return idade;
    }

    public String getProfissao() {
        return profissao;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getProblemas() {
        return problemas;
    }

    public String getMcdts() {
        return mcdts;
    }

    public String getReferenciacao() {
        return referenciacao;
    }

    public String getGestos() {
        return gestos;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getData() {
        return data;
    }

    /** SETTERS */

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAutonomia(String autonomia) {
        this.autonomia = autonomia;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setProblemas(String problemas) {
        this.problemas = problemas;
    }

    public void setMcdts(String mcdts) {
        this.mcdts = mcdts;
    }

    public void setReferenciacao(String referenciacao) {
        this.referenciacao = referenciacao;
    }

    public void setGestos(String gestos) {
        this.gestos = gestos;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setData(String data) {
        this.data = data;
    }

    /** EXTRAS */

    public Consulta toConsulta() {
        return null;
        //todo
    }

    public static ConsultaDTO toConsultaDTO(Consulta consulta){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setProcesso(consulta.getProcesso());
        consultaDTO.setTipo(consulta.getTipo().toString());
        consultaDTO.setAutonomia(consulta.getAutonomia().toString());
        consultaDTO.setSexo(consulta.getSexo().toString());
        consultaDTO.setIdade(consulta.getIdade());
        consultaDTO.setProfissao(consulta.getProfissao());
        consultaDTO.setMotivo(consulta.getMotivo());
        consultaDTO.setProblemas(consulta.getProblemas());
        consultaDTO.setMcdts(consulta.getMcdts());
        consultaDTO.setReferenciacao(consulta.getReferenciacao());
        consultaDTO.setGestos(consulta.getGestos());
        consultaDTO.setObservacoes(consulta.getObservacoes());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(consulta.getData());
        consultaDTO.setData(format);
        return consultaDTO;
    }
}
