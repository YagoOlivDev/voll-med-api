package med.voll.api.Controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.Domain.Consultas.AgendaDeConsultas;
import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.Consultas.DetalhamentoDadosConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name="bearer-key")
public class ConsultaController
{
    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados)
    {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
}
