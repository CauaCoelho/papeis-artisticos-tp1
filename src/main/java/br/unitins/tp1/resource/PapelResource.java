package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.PapelDTO;
import br.unitins.tp1.dto.PapelDTOResponse;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.PapelService;
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

@Path("/papeis")
@Produces(MediaType.APPLICATION_JSON) //Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) //Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para todos os métodos
public class PapelResource {


    @Inject //injeção de dependência
    PapelService service;

    @GET
    public List<Papel> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public List <Papel> buscarPorTextura (Textura textura) {
        return service.findByTextura(textura);
    }

    @RolesAllowed("Administrador")
    @POST
    public PapelDTOResponse incluir (PapelDTO dto){
        return service.create(dto);
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("/{id}")
    @Transactional
    public void editar(Long id, PapelDTO dto){
        service.update(id, dto);
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("/{id}")
    @Transactional
    public void excluir(Long id){
        service.delete(id);
    }
}
