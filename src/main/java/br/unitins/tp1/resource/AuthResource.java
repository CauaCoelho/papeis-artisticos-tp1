package br.unitins.tp1.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.service.UsuarioService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Resource de Autenticação integrado com Keycloak via OIDC.
 * 
 * O fluxo de login é gerenciado pelo Keycloak:
 * 1. Frontend redireciona para Keycloak para login
 * 2. Keycloak retorna JWT token
 * 3. Frontend envia JWT em Authorization header
 * 4. Quarkus valida o JWT automaticamente via OIDC
 * 
 * Este resource apenas fornece endpoints para dados do usuário autenticado.
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    br.unitins.tp1.service.UsuarioLogadoService usuarioLogadoService;

    /**
     * Retorna dados do usuário autenticado.
     * Extrai informações do token JWT (OIDC claims).
     * 
     * @return UsuarioDTOResponse com dados do usuário
     */
    @GET
    @Path("/me")
    @Authenticated
    public Response me() {
        br.unitins.tp1.model.Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return Response.ok(UsuarioDTOResponse.valueOf(usuario)).build();
    }
}
