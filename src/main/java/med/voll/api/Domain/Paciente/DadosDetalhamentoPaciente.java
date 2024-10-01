package med.voll.api.Domain.Paciente;

import med.voll.api.Domain.Enderecos.Endereco;

public record DadosDetalhamentoPaciente(Long id,
                                        String nome,
                                        String email,
                                        String telefone,
                                        String cpf,
                                        Endereco endereco)
{
    public DadosDetalhamentoPaciente(Paciente paciente)
    {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(),
                paciente.getTelefone(), paciente.getEndereco());
    }
}

