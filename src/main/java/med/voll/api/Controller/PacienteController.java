package med.voll.api.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.Domain.Paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name="bearer-key")
public class PacienteController
{

    @Autowired
    private PacienteRepository repository;


    //Cadastra novos pacientes no banco de dados
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados,
                                    UriComponentsBuilder uriBuilder)
    {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    //Retorna uma pagina de 10 pacientes.
    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>>listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao)
    {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    //Buscando um paciente pelo ID e alterando seus dados
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados)
    {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }


    //Deixa o perfil do paciente inativo permancendo com os dados no database.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id)
    {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhes(@PathVariable Long id)
    {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
