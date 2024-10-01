package med.voll.api.Domain.Consultas.Validacoes;

import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsultas
{
    public void validar(DadosAgendamentoConsulta dados)
    {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferençaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(diferençaEmMinutos < 30)
        {
            throw new ValidacaoException
                    ("Consulta deve ser agendada com antecedencia de 30minutos");
        }
    }
}
