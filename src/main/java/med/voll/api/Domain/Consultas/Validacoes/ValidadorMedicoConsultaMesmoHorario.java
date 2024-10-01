package med.voll.api.Domain.Consultas.Validacoes;

import med.voll.api.Domain.Consultas.ConsultaRepository;
import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConsultaMesmoHorario implements ValidadorAgendamentoConsultas
{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados)
    {
        var medidoConsultaMesmoHorario = consultaRepository.existsByMedicoIdAndData
                (dados.idMedico(), dados.data());

        if (medidoConsultaMesmoHorario)
        {
            throw new ValidacaoException("Este médico já possui consulta no mesmo horario");
        }
    }
}
