package med.voll.api.Domain.Consultas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>
{

    Boolean existsByMedicoIdAndData(Long id, LocalDateTime data);

    Boolean existsByPacienteIdAndDataBetween(Long idMedico, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
