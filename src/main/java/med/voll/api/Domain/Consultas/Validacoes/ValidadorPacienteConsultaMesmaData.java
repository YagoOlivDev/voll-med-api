package med.voll.api.Domain.Consultas.Validacoes;

import med.voll.api.Domain.Consultas.ConsultaRepository;
import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteConsultaMesmaData implements ValidadorAgendamentoConsultas
{
    @Autowired
    private ConsultaRepository repository;


    public void validar(DadosAgendamentoConsulta dados)
    {
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween
                (dados.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiOutraConsultaNoDia)
        {
            throw new ValidacaoException("Paciente não pode ter mais de uma consulta no mesmo dia");
        }
    }
}
