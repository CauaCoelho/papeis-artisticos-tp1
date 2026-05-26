package br.unitins.tp1.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.dto.WishlistDTO;
import br.unitins.tp1.dto.WishlistDTOResponse;
import br.unitins.tp1.service.UsuarioService;
import br.unitins.tp1.service.WishlistService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Resource para gerenciar a lista de desejos (wishlist) do usuário.
 * 
 * Endpoints:
 * - POST /wishlist/{produtoId} : Adicionar produto à wishlist
 * - GET /wishlist : Listar todos os itens da wishlist
 * - DELETE /wishlist/{produtoId} : Remover produto da wishlist
 * 
 * Todas as operações usam o usuário extraído do token JWT.
 */
@Path("/wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WishlistResource {

    @Inject
    WishlistService service;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    /**
     * Adiciona um produto à wishlist do usuário autenticado.
     * 
     * @param produtoId ID do produto a ser adicionado
     * @return Resposta com o item adicionado à wishlist
     */
    @POST
    @Path("/{produtoId}")
    @Authenticated
    @RolesAllowed("USER")
    @Transactional
    public Response adicionar(@PathParam("produtoId") Long produtoId) {
        // Extrai o usuário do token JWT
        Long usuarioId = obterIdUsuarioDoToken();

        WishlistDTO dto = new WishlistDTO(produtoId);
        WishlistDTOResponse resultado = service.adicionar(usuarioId, dto);

        return Response.status(Status.CREATED).entity(resultado).build();
    }

    /**
     * Lista todos os itens da wishlist do usuário autenticado.
     * 
     * @return Lista de itens da wishlist
     */
    @GET
    @Authenticated
    @RolesAllowed("USER")
    public Response listar() {
        // Extrai o usuário do token JWT
        Long usuarioId = obterIdUsuarioDoToken();

        List<WishlistDTOResponse> wishlist = service.listar(usuarioId);
        return Response.ok(wishlist).build();
    }

    /**
     * Remove um produto da wishlist do usuário autenticado.
     * 
     * @param produtoId ID do produto a ser removido
     * @return Resposta vazia com status 204 No Content
     */
    @DELETE
    @Path("/{produtoId}")
    @Authenticated
    @RolesAllowed("USER")
    @Transactional
    public Response remover(@PathParam("produtoId") Long produtoId) {
        // Extrai o usuário do token JWT
        Long usuarioId = obterIdUsuarioDoToken();

        service.remover(usuarioId, produtoId);
        return Response.noContent().build();
    }

    /**
     * Extrai o ID do usuário a partir do token JWT.
     * 
     * @return ID do usuário
     * @throws WebApplicationException se o usuário não for encontrado
     */
    private Long obterIdUsuarioDoToken() {
        String sub = jwt.getSubject();
        var usuarioDTOResponse = usuarioService.findBySub(sub);
        
        if (usuarioDTOResponse == null) {
            throw new jakarta.ws.rs.WebApplicationException(
                "Usuário não encontrado",
                Status.UNAUTHORIZED
            );
        }
        
        return usuarioDTOResponse.id();
    }
}
