package med.voll.api.Domain.Consultas.Validacoes;

import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsultas
{
    public void validar(DadosAgendamentoConsulta dados)
    {
        var dataConsulta= dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = dataConsulta.getHour() < 7;
        var depoisDoEncerramento = dataConsulta.getHour() > 18;

        if(domingo || antesDaAbertura || depoisDoEncerramento)
        {
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }
}
