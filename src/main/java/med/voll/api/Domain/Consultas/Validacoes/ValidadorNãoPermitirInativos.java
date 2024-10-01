package med.voll.api.Domain.Consultas.Validacoes;

import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.Medico.MedicoRepository;
import med.voll.api.Domain.Paciente.PacienteRepository;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNãoPermitirInativos implements ValidadorAgendamentoConsultas
{
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados)
    {
        if (dados.idMedico() == null)
        {
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo)
        {
            throw new ValidacaoException("Medico está invativo");
        }

        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!pacienteEstaAtivo)
        {
            throw new ValidacaoException("Paciente está inativo");
        }
    }
}
