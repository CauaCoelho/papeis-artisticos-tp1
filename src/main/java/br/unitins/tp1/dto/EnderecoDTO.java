package br.unitins.tp1.dto;

public record EnderecoDTO(
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado
) {}