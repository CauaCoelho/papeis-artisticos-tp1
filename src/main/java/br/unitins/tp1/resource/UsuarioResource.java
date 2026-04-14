package br.unitins.tp1.resource;


import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.service.UsuarioService;
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
@Produces(MediaType.APPLICATION_JSON) //Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) //Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para todos os métodos
public class UsuarioResource {


    @Inject //injeção de dependência
    UsuarioService service;

    @GET
    public Response buscarTodos(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public UsuarioDTOResponse buscarPorId (Long id) {
        return service.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response editar(@PathParam("id")Long id, @Valid UsuarioDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id")Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
