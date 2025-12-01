package br.unitins.tp1.dto;

public record AdministradorDTO(
    String nome,
    String login, 
    String senha,
    Integer idPrioridade
) {

}
