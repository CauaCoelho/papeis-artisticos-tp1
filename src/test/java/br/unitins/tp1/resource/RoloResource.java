package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.RoloDTO;
import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.RoloService;
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
public class RoloResource {


    @Inject //injeção de dependência
    RoloService service;

    @GET
    public List<Rolo> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public List <Rolo> buscarPorTextura (Textura textura) {
        return service.findByTextura(textura);
    }

    @POST
    public Rolo incluir (RoloDTO dto){
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void editar(Long id, RoloDTO dto){
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void excluir(Long id){
        service.delete(id);
    }
}
