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
import jakarta.ws.rs.core.Response.Status;

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
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

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
        // Extrai o 'sub' (Subject) do token JWT - ID do usuário no Keycloak
        String sub = jwt.getSubject();
        
        // Busca o usuário no banco pela chave do Keycloak
        UsuarioDTOResponse usuario = usuarioService.findBySub(sub);

        if (usuario == null) {
            throw new jakarta.ws.rs.WebApplicationException("Usuario nao encontrado", Status.UNAUTHORIZED);
        }

        return Response.ok(usuario).build();
    }
}
