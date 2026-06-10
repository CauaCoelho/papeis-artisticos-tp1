package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.EnderecoDTOResponse;
import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.service.EnderecoServiceImpl;
import br.unitins.tp1.service.UsuarioService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON) // Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) // Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para
                                      // todos os métodos
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @Inject
    EnderecoServiceImpl enderecoService;

    @RolesAllowed("ADMIN")
    @GET
    public Response buscarTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return Response.ok(service.findAll()).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/{id}")
    public UsuarioDTOResponse buscarPorId(
            @PathParam("id") Long id) {
        return service.findById(id);
    }

    @RolesAllowed("ADMIN")
    @PUT
    @Path("/{id}")
    @Transactional
    public Response editar(@PathParam("id") Long id, @Valid UsuarioDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    /**
     * Lista endereços do usuário.
     * Requer autenticação JWT para garantir que só o próprio usuário acesse.
     */
    @GET
    @Path("/{id}/enderecos")
    @Authenticated
    public Response listarEnderecos(@PathParam("id") Long id) {
        List<EnderecoDTOResponse> enderecos = enderecoService.findByUsuario(id);
        return Response.ok(enderecos).build();
    }
}
