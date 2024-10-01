package med.voll.api.Domain.Medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.Domain.Enderecos.Endereco;

public record AtualizarDadosMedico(@NotNull
                                   Long id,
                                   String nome,
                                   String telefone,
                                   Endereco endereco)
{

}
