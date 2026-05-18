package br.unitins.tp1.dto;

import br.unitins.tp1.model.Perfil;

public record UsuarioDTO(
                String nome,
                String username,
                String login,
                String senha,
                Perfil perfil) {

}
