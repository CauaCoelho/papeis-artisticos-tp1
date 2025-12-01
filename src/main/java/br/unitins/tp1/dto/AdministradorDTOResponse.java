package br.unitins.tp1.dto;

import br.unitins.tp1.model.Prioridade;
import br.unitins.tp1.model.Administrador;

public record AdministradorDTOResponse(
    Long id,
    String nome,
    String login,
    String senha,
    Prioridade prioridade
) {
    public static AdministradorDTOResponse valueOf(Administrador administrador){
        if(administrador == null)
            return null;
        return new AdministradorDTOResponse(
            administrador.getId(),
            administrador.getNome(),
            administrador.getLogin(),
            administrador.getSenha(), 
            administrador.getPrioridade()
        );
    }

}
