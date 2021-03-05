package facade.services;

import business.ConsultaHandler;
import facade.DTO.ConsultaDTO;
import facade.exceptions.ApplicationException;

import java.util.List;

public class ConsultaOperationsService {

    private ConsultaHandler consultaHandler;

    public ConsultaOperationsService(ConsultaHandler consultaHandler) {
        this.consultaHandler = consultaHandler;
    }

    public ConsultaDTO getConsulta(int processo) throws ApplicationException {
        return consultaHandler.getConsulta(processo);
    }

    public List<ConsultaDTO> getAllConsultas() throws ApplicationException {
        return consultaHandler.getAllConsultas();
    }

    public boolean addConsulta(int processo, String tipo, String autonomia, String sexo, int idade,
                            String profissao, String motivo, String problemas, String mcdts,
                            String referenciacao, String gestos, String observacoes, String data)
            throws ApplicationException {
        return consultaHandler.addConsulta(processo, tipo, autonomia, sexo, idade, profissao, motivo, problemas,
                mcdts, referenciacao, gestos, observacoes, data);
    }

    public List<Integer> getAllProcessos() throws ApplicationException {
        return consultaHandler.getAllProcessos();
    }

    public boolean removeConsulta(int processo) throws ApplicationException {
        return consultaHandler.removeConsulta(processo);
    }

    public boolean updateConsulta(int processoToChange, int processo, String tipo, String autonomia, String sexo,
                                  int idade, String profissao, String motivo, String problemas, String mcdts,
                                  String referenciacao, String gestos, String observacoes, String data)
            throws ApplicationException {
        return consultaHandler.updateConsulta(processoToChange, processo, tipo, autonomia, sexo, idade, profissao,
                motivo, problemas, mcdts, referenciacao, gestos, observacoes, data);
    }

    public boolean removeAllConsultas() throws ApplicationException {
        return consultaHandler.removeAllConsultas();
    }







    public List<ConsultaDTO> filterByTipo(String operation, String selectedValue) throws ApplicationException {
        return consultaHandler.filterByTipo(operation, selectedValue);
    }

    public List<ConsultaDTO> filterByAutonomia(String operation, String selectedValue) throws ApplicationException {
        return consultaHandler.filterByAutonomia(operation, selectedValue);
    }

    public List<ConsultaDTO> filterByGenero(String operation, String selectedValue) throws ApplicationException {
        return consultaHandler.filterByGenero(operation, selectedValue);
    }
}
