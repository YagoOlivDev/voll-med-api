package med.voll.api.Domain.Consultas;

import med.voll.api.Domain.Consultas.Validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.Domain.Medico.DadosDetalhamentoMedico;
import med.voll.api.Domain.Medico.Medico;
import med.voll.api.Domain.Medico.MedicoRepository;
import med.voll.api.Domain.Paciente.PacienteRepository;
import med.voll.api.Domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AgendaDeConsultas
{
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

    public DetalhamentoDadosConsulta agendar(DadosAgendamentoConsulta dados)
    {
        if (!pacienteRepository.existsById(dados.idPaciente()))
        {
            throw new ValidacaoException("Id do paciente não existe!");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
        {
            throw new ValidacaoException("Id do medico não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null)
        {
            throw new ValidacaoException("Não existe médico disponivel nessa data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DetalhamentoDadosConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados)
    {
        if (dados.idMedico() != null)
        {
           return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null)
        {
            throw new ValidacaoException
                    ("Especialidade é obrigatório quando médico não for escolhido");
        }

        return medicoRepository.EscolherMedicoAleatorio(dados.especialidade(), dados.data());
    }
}
