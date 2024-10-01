package med.voll.api.Domain.Consultas;

import java.time.LocalDateTime;

public record DetalhamentoDadosConsulta(Long id,
                                        Long idMedico,
                                        Long idPaciente,
                                        LocalDateTime data)
{

    public DetalhamentoDadosConsulta(Consulta consulta)
    {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(),
                consulta.getData());
    }
}
