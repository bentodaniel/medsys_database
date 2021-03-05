package business;

import business.enums.OperationType;
import business.enums.TipoAutonomia;
import business.enums.TipoConsulta;
import business.enums.TipoGenero;
import business.exceptions.ConsultaBusinessException;
import business.exceptions.ConsultaNotFoundException;
import facade.exceptions.ApplicationException;
import facade.DTO.ConsultaDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaHandler {

    private EntityManagerFactory emf;

    public ConsultaHandler(EntityManagerFactory emf){
        this.emf = emf;
    }

    public ConsultaDTO getConsulta(int processo) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            em.getTransaction().begin();
            Consulta queryResult = consultaCatalogo.getConsulta(processo);
            em.getTransaction().commit();

            return ConsultaDTO.toConsultaDTO(queryResult);

        } catch (ConsultaNotFoundException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao procurar consulta.", e);
        } finally {
            em.close();
        }
    }

    public List<ConsultaDTO> getAllConsultas() throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.getAllConsultas();
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao procurar consultas.", e);
        } finally {
            em.close();
        }
    }

    public boolean addConsulta(int processo, String tipo, String autonomia, String sexo, int idade, String profissao,
                            String motivo, String problemas, String mcdts, String referenciacao, String gestos,
                            String observacoes, String data) throws ApplicationException {

        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        boolean result = false;

        try {
            em.getTransaction().begin();

            //parse values
            TipoConsulta tipoConsulta = TipoConsulta.valueOf(tipo);
            TipoAutonomia tipoAutonomia = TipoAutonomia.valueOf(autonomia);
            TipoGenero tipoGenero = TipoGenero.valueOf(sexo);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateParsed = format.parse(data);

            // call on catalog
            consultaCatalogo.addConsulta(processo, tipoConsulta, tipoAutonomia, tipoGenero, idade, profissao,
                    motivo, problemas, mcdts, referenciacao, gestos, observacoes, dateParsed);

            em.getTransaction().commit();

            result = true;
        }
        catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao adicionar a consulta.", e);
        }
        finally {
            em.close();
        }
        return result;
    }

    public List<Integer> getAllProcessos() throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        List<Integer> result;
        try {
            em.getTransaction().begin();
            result = consultaCatalogo.getProcessosList();
            em.getTransaction().commit();
        }
        catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao encontrar processos.", e);
        }
        finally {
            em.close();
        }
        return result;
    }

    public boolean removeConsulta(int processo) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        boolean result = false;

        try {
            em.getTransaction().begin();
            consultaCatalogo.removeConsulta(processo);
            em.getTransaction().commit();
            result = true;
        }
        catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao remover consulta.", e);
        }
        finally {
            em.close();
        }
        return result;
    }

    public boolean updateConsulta(int processoToChange, int processo, String tipo, String autonomia, String  sexo,
                               int idade, String profissao, String motivo, String problemas, String mcdts,
                                  String referenciacao, String gestos, String observacoes, String data) throws ApplicationException {

        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        boolean result = false;

        try {
            em.getTransaction().begin();

            //parse values
            TipoConsulta tipoConsulta = TipoConsulta.valueOf(tipo);
            TipoAutonomia tipoAutonomia = TipoAutonomia.valueOf(autonomia);
            TipoGenero tipoGenero = TipoGenero.valueOf(sexo);

            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date dateParsed = format.parse(data);

            // call on catalog
            consultaCatalogo.updateConsulta(processoToChange, processo, tipoConsulta, tipoAutonomia, tipoGenero,
                    idade, profissao, motivo, problemas, mcdts, referenciacao, gestos, observacoes, dateParsed);

            em.getTransaction().commit();

            result = true;
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao atualizar a consulta.", e);
        }
        finally {
            em.close();
        }
        return result;
    }

    public boolean removeAllConsultas() throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        boolean result = false;
        try {
            em.getTransaction().begin();
            consultaCatalogo.removeAllConsultas();
            em.getTransaction().commit();

            result = true;
        }
        catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao procurar consulta.", e);
        }
        finally {
            em.close();
        }
        return result;
    }

    public List<ConsultaDTO> filterByProcesso(String operation, int value, int min, int max) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            OperationType operationType = OperationType.valueOf(operation);

            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.filterGetAllConsultasByProcesso(operationType, value, min, max);
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao filtrar consultas.", e);
        } finally {
            em.close();
        }
    }

    public List<ConsultaDTO> filterByTipo(String operation, String selectedValue) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            OperationType operationType = OperationType.valueOf(operation);
            TipoConsulta tipo = TipoConsulta.valueOf(selectedValue);

            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.filterGetAllConsultasByTipo(operationType, tipo);
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao filtrar consultas.", e);
        } finally {
            em.close();
        }
    }

    public List<ConsultaDTO> filterByAutonomia(String operation, String selectedValue) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            OperationType operationType = OperationType.valueOf(operation);
            TipoAutonomia autonomia = TipoAutonomia.valueOf(selectedValue);

            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.filterGetAllConsultasByAutonomia(operationType, autonomia);
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao filtrar consultas.", e);
        } finally {
            em.close();
        }
    }

    public List<ConsultaDTO> filterByGenero(String operation, String selectedValue) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            OperationType operationType = OperationType.valueOf(operation);
            TipoGenero genero = TipoGenero.valueOf(selectedValue);

            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.filterGetAllConsultasByGenero(operationType, genero);
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao filtrar consultas.", e);
        } finally {
            em.close();
        }
    }

    public List<ConsultaDTO> filterByIdade(String operation, int value, int min, int max) throws ApplicationException {
        EntityManager em = emf.createEntityManager();
        ConsultaCatalogo consultaCatalogo = new ConsultaCatalogo(em);
        try {
            OperationType operationType = OperationType.valueOf(operation);

            em.getTransaction().begin();
            Iterable<Consulta> queryResult = consultaCatalogo.filterGetAllConsultasByIdade(operationType, value, min, max);
            em.getTransaction().commit();

            List<ConsultaDTO> result = new ArrayList<>();
            queryResult.forEach(e -> result.add(ConsultaDTO.toConsultaDTO(e)));
            return result;

        } catch (ConsultaBusinessException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException(e.getMessage(), e);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ApplicationException("Erro ao filtrar consultas.", e);
        } finally {
            em.close();
        }
    }
}
