package br.unitins.tp1.dto;

/**
 * DTO para requisições de usuário.
 * Senha é gerenciada pelo Keycloak, não deve ser incluída aqui.
 * Sub (ID do Keycloak) é extraído automaticamente do token JWT.
 */
public record UsuarioDTO(
        String nome,
        String login,
        String username,
        int idPerfil) {

}
