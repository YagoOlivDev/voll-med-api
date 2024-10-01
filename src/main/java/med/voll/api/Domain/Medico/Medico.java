package med.voll.api.Domain.Medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.Domain.Enderecos.Endereco;


@Table(name="medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedicos dados)
    {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(AtualizarDadosMedico dados)
    {
        if(dados.nome() != null)
        {
            this.nome = dados.nome();
        }
        if(dados.endereco() != null)
        {
            this.endereco = dados.endereco();
        }
        if(dados.telefone() != null)
        {
            this.telefone = dados.telefone();
        }
    }

    public void excluir()
    {
        this.ativo = false;
    }
}
