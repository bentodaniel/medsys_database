package business;

import business.enums.TipoGenero;
import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = Consulta.GET_ALL, query = "SELECT c FROM Consulta c"),
        @NamedQuery(name = Consulta.GET_ALL_PROCESSOS, query = "SELECT c.processo FROM Consulta c"),
        @NamedQuery(name = Consulta.GET_BY_PROCESSO, query = "SELECT c FROM Consulta c WHERE c.processo = :" + Consulta.PROCESSO),
        @NamedQuery(name = Consulta.REMOVE_ALL, query = "DELETE FROM Consulta")
})
public class Consulta {

    public static final String GET_ALL = "Consulta.getAll";
    public static final String GET_ALL_PROCESSOS = "Consulta.getAllProcessos";
    public static final String GET_BY_PROCESSO = "Consulta.getByProcesso";
    public static final String REMOVE_ALL = "Consulta.removeAll";

    public static final String PROCESSO = "processo";

    @Id()
    private int processo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoConsulta tipo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAutonomia autonomia;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoGenero sexo;

    @Column(nullable = false)
    private int idade;

    @Column()
    private String profissao;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String problemas;

    @Column(nullable = false)
    private String mcdts;

    @Column(nullable = false)
    private String referenciacao;

    @Column(nullable = false)
    private String gestos;

    @Column(nullable = false)
    private String observacoes;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;

    /**
     * CONSTRUCTORS
     */

    public Consulta() {
    }

    public Consulta(int processo, TipoConsulta tipo, TipoAutonomia autonomia, TipoGenero sexo,
                    int idade, String profissao, String motivo, String problemas,
                    String mcdts, String referenciacao, String gestos, String observacoes, Date data) {
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

    public TipoConsulta getTipo() {
        return tipo;
    }

    public TipoAutonomia getAutonomia() {
        return autonomia;
    }

    public TipoGenero getSexo() {
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

    public Date getData() {
        return data;
    }

    /** SETTERS */

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public void setTipo(TipoConsulta tipo) {
        this.tipo = tipo;
    }

    public void setAutonomia(TipoAutonomia autonomia) {
        this.autonomia = autonomia;
    }

    public void setSexo(TipoGenero sexo) {
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

    public void setData(Date data) {
        this.data = data;
    }
}
