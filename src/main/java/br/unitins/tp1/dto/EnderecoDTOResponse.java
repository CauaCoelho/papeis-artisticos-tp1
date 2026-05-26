package br.unitins.tp1.dto;

import br.unitins.tp1.model.Endereco;

public record EnderecoDTOResponse(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado) {
    public static EnderecoDTOResponse valueOf(Endereco endereco) {
        return new EnderecoDTOResponse(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado());
    }
}