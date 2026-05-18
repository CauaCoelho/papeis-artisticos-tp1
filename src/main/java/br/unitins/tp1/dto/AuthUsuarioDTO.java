package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthUsuarioDTO(
        @NotBlank(message = "") String login,

        @NotBlank(message = "") String senha) {

}
