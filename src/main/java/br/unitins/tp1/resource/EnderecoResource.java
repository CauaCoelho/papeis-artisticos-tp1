package br.unitins.tp1.resource;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.EnderecoDTOResponse;
import br.unitins.tp1.service.EnderecoServiceImpl;
import br.unitins.tp1.service.UsuarioLogadoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Resource para gerenciar endereços do usuário autenticado.
 * 
 * Endpoints:
 * - GET /usuarios/{id}/enderecos : Listar endereços do usuário autenticado
 * - POST /enderecos : Criar novo endereço (associado ao usuário logado via JWT)
 * - PUT /enderecos/{id} : Atualizar endereço
 * - DELETE /enderecos/{id} : Remover endereço
 */
@RolesAllowed("USER")
@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EnderecoResource {

    @Inject
    EnderecoServiceImpl enderecoService;

    @Inject
    UsuarioLogadoService usuarioLogadoService;

    /**
     * Cria um novo endereço associado ao usuário autenticado.
     * O ID do usuário é extraído do JWT pelo UsuarioLogadoService.
     */
    @POST
    @Transactional
    public Response criar(EnderecoDTO dto) {
        Long usuarioId = usuarioLogadoService.getIdUsuarioLogado();
        EnderecoDTOResponse response = enderecoService.create(dto, usuarioId);
        return Response.status(Status.CREATED).entity(response).build();
    }

    /**
     * Atualiza um endereço existente.
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, EnderecoDTO dto) {
        enderecoService.update(id, dto);
        return Response.noContent().build();
    }

    /**
     * Remove um endereço.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response remover(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.noContent().build();
    }
}
