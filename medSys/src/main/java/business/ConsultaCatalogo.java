package business;

import business.enums.OperationType;
import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;
import business.enums.TipoGenero;
import business.exceptions.ConsultaBusinessException;
import business.exceptions.ConsultaNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Realiza o catalogo de consultas
 */
public class ConsultaCatalogo {

    private EntityManager em;

    /**
     * Cria um construtor para a class ConsultaCatalogo
     * @param em O EntityManager a ser usado
     */
    public ConsultaCatalogo(EntityManager em) {
        this.em = em;
    }

    /**
     * Obtem uma consulta a partir de um id de processo
     * @param processoId O processo a procurar
     * @return A consulta registada com o processo dado
     * @throws ConsultaNotFoundException Se nao houver uma consulta com o numero de processo dado
     */
    public Consulta getConsulta(int processoId) throws ConsultaNotFoundException {
        try {
            TypedQuery<Consulta> query = em.createNamedQuery(Consulta.GET_BY_PROCESSO, Consulta.class);
            query.setParameter(Consulta.PROCESSO, processoId);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            throw new ConsultaNotFoundException("Consulta " + processoId + " nao existe!");
        }
    }

    /**
     * Busca todas as consultas na BD
     * @return Uma lista com todas as consultas registadas na BD
     * @throws ConsultaBusinessException Se algo correr mal
     */
    public List<Consulta> getAllConsultas() throws ConsultaBusinessException {
        try {
            TypedQuery<Consulta> query = em.createNamedQuery(Consulta.GET_ALL, Consulta.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel encontrar nenhuma consulta");
        }
    }

    /**
     * Adiciona uma consulta a BD, se nao houver nenhuma outra consulta com o numero de processo dado
     * @throws ConsultaBusinessException Se algo correr mal
     */
    public void addConsulta(int processo, TipoConsulta tipo, TipoAutonomia autonomia, TipoGenero sexo, int idade,
                            String profissao, String motivo, String problemas, String mcdts, String referenciacao,
                            String gestos, String observacoes, Date data) throws ConsultaBusinessException {
        try {
            getConsulta(processo);
            throw new ConsultaBusinessException("Ja existe uma consulta registada com o numero de processo indicado.");
        }
        catch (ConsultaNotFoundException e) {
            //nao existe uma consulta com o id dado
            Consulta consulta = new Consulta(processo, tipo, autonomia, sexo, idade, profissao,
                    motivo, problemas, mcdts, referenciacao, gestos, observacoes, data);
            em.persist(consulta);
        }
    }

    /**
     * Obtem uma lista dos processos na BD
     * @return Lista com os numeros de processos na BD
     * @throws ConsultaBusinessException Se algo correr mal
     */
    public List<Integer> getProcessosList() throws ConsultaBusinessException {
        try {
            TypedQuery<Integer> query = em.createNamedQuery(Consulta.GET_ALL_PROCESSOS, Integer.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel obter uma lista de processos");
        }
    }

    /**
     * Remove uma consulta com um dado numero de processo da BD
     * @param processo O processo a ser removido
     * @throws ConsultaBusinessException Se algo correr mal
     */
    public void removeConsulta(int processo) throws ConsultaBusinessException {
        try {
            Consulta consulta = em.find(Consulta.class, processo);
            em.remove(consulta);
        }
        catch (Exception e){
            throw new ConsultaBusinessException("Nao foi possivel remover a consulta");
        }
    }

    /**
     * Atualiza os dados de uma consulta com um dado numero de processo
     * @param processoToChange O processo a ser atualizado
     */
    public void updateConsulta(int processoToChange, int processo, TipoConsulta tipo, TipoAutonomia autonomia,
                               TipoGenero sexo, int idade, String profissao, String motivo, String problemas,
                               String mcdts, String referenciacao, String gestos, String observacoes, Date data) {

        Consulta consulta = em.find(Consulta.class, processoToChange);

        consulta.setProcesso(processo);
        consulta.setTipo(tipo);
        consulta.setAutonomia(autonomia);
        consulta.setSexo(sexo);
        consulta.setIdade(idade);
        consulta.setProfissao(profissao);
        consulta.setMotivo(motivo);
        consulta.setProblemas(problemas);
        consulta.setMcdts(mcdts);
        consulta.setReferenciacao(referenciacao);
        consulta.setGestos(gestos);
        consulta.setObservacoes(observacoes);
        consulta.setData(data);
    }

    public void removeAllConsultas() throws ConsultaBusinessException {
        try {
            Query query = em.createNamedQuery(Consulta.REMOVE_ALL);
            query.executeUpdate();
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel limpar a base de dados");
        }
    }

    public List<Consulta> filterGetAllConsultasByTipo(OperationType operation, TipoConsulta tipo)
            throws ConsultaBusinessException {
        try {
            TypedQuery<Consulta> query = null;
            switch (operation) {
                case EQUALS:
                    query = em.createNamedQuery(Consulta.GET_BY_TIPO_EQUALS, Consulta.class);
                    query.setParameter(Consulta.TIPO, tipo);
                    break;

                case DIFFERENT:
                    query = em.createNamedQuery(Consulta.GET_BY_TIPO_DIFFERENT, Consulta.class);
                    query.setParameter(Consulta.TIPO, tipo);
                    break;
            }
            if (query != null) {
                return query.getResultList();
            }
            else {
                throw new ConsultaBusinessException("Algo correu mal ao tentar filtrar as consultas");
            }
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel filtrar as consultas");
        }
    }

    public List<Consulta> filterGetAllConsultasByAutonomia(OperationType operation, TipoAutonomia autonomia)
            throws ConsultaBusinessException {
        try {
            TypedQuery<Consulta> query = null;
            switch (operation) {
                case EQUALS:
                    query = em.createNamedQuery(Consulta.GET_BY_AUTONOMIA_EQUALS, Consulta.class);
                    query.setParameter(Consulta.AUTONOMIA, autonomia);
                    break;

                case DIFFERENT:
                    query = em.createNamedQuery(Consulta.GET_BY_AUTONOMIA_DIFFERENT, Consulta.class);
                    query.setParameter(Consulta.AUTONOMIA, autonomia);
                    break;
            }
            if (query != null) {
                return query.getResultList();
            }
            else {
                throw new ConsultaBusinessException("Algo correu mal ao tentar filtrar as consultas");
            }
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel filtrar as consultas");
        }
    }

    public List<Consulta> filterGetAllConsultasByGenero(OperationType operation, TipoGenero genero)
            throws ConsultaBusinessException {
        try {
            TypedQuery<Consulta> query = null;
            switch (operation) {
                case EQUALS:
                    query = em.createNamedQuery(Consulta.GET_BY_SEXO_EQUALS, Consulta.class);
                    query.setParameter(Consulta.SEXO, genero);
                    break;

                case DIFFERENT:
                    query = em.createNamedQuery(Consulta.GET_BY_SEXO_DIFFERENT, Consulta.class);
                    query.setParameter(Consulta.SEXO, genero);
                    break;
            }
            if (query != null) {
                return query.getResultList();
            }
            else {
                throw new ConsultaBusinessException("Algo correu mal ao tentar filtrar as consultas");
            }
        } catch (PersistenceException e) {
            throw new ConsultaBusinessException("Nao foi possivel filtrar as consultas");
        }
    }
}
