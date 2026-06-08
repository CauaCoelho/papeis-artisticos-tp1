package br.unitins.tp1.dto;

import br.unitins.tp1.model.Endereco;

public record EnderecoDTOResponse(
        Long id,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado) {
    public static EnderecoDTOResponse valueOf(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoDTOResponse(
                endereco.getId(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado());
    }
}