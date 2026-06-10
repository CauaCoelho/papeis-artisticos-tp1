package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;

public record UsuarioDTOResponse(
        Long id,
        String nome,
        String username,
        String login,
        Perfil perfil) {
    public static UsuarioDTOResponse valueOf(Usuario usuario) {
        if (usuario == null)
            return null;
        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getUsername(),
                usuario.getLogin(),
                usuario.getPerfil());
    }

}
