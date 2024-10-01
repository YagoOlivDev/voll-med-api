package med.voll.api.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.Domain.Medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("medicos")
@SecurityRequirement(name="bearer-key")
public class MedicoController
{
    @Autowired
    private MedicoRepository repository;

    //Cadastra novos médicos no banco de dados
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados,
                                    UriComponentsBuilder uriBuilder)
    {
        var medico = new Medico(dados);
        repository.save(medico);

        var uri =  uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    //Retorna uma pagina de 10 médicos.
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>>listar(@PageableDefault(size=10) Pageable paginacao)
    {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    //Buscando um médico pelo ID e alterando seus dados
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid AtualizarDadosMedico dados)
    {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    //Deixa o perfil do médico inativo permancendo com os dados no database.
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id)
    {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhes(@PathVariable Long id)
    {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
