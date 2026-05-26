package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Administrador;

public record AdministradorDTOResponse(
        Long id,
        String nome,
        String login,
        String senha,
        Perfil perfil) {
    public static AdministradorDTOResponse valueOf(Administrador administrador) {
        if (administrador == null)
            return null;
        return new AdministradorDTOResponse(
                administrador.getId(),
                administrador.getNome(),
                administrador.getLogin(),
                administrador.getSenha(),
                administrador.getPerfil());
    }

}
