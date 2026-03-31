package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.BlocoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/blocos")
@Produces(MediaType.APPLICATION_JSON) //Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) //Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para todos os métodos
public class BlocoResource {


    @Inject //injeção de dependência
    BlocoService service;

    @GET
    public Response buscarTodos(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public List <Bloco> buscarPorTextura (Textura textura) {
        return service.findByTextura(textura);
    }

    @RolesAllowed("Administrador")
    @POST
    public Response incluir (BlocoDTO dto){
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("/{id}")
    @Transactional
    public Response editar(@PathParam("id")Long id, @Valid BlocoDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id")Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
