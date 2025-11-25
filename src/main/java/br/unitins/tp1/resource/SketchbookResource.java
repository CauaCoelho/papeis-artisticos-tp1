package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.SketchbookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/sketchbooks")
@Produces(MediaType.APPLICATION_JSON) //Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) //Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para todos os métodos
public class SketchbookResource {


    @Inject //injeção de dependência
    SketchbookService service;

    @GET
    public List<Sketchbook> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public List <Sketchbook> buscarPorTextura (Textura textura) {
        return service.findByTextura(textura);
    }

    @RolesAllowed("Administrador")
    @POST
    public Response incluir (SketchbookDTO dto){
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("/{id}")
    @Transactional
    public Response editar(Long id, SketchbookDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
