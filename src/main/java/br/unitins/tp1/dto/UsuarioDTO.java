package br.unitins.tp1.dto;

public record UsuarioDTO(
    String nome,
    String login, 
    String senha,
    Integer idPrioridade
) {

}
