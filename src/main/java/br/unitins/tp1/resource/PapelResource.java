package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.PapelDto;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.service.PapelService;
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
    @Path ("/find/{modelo}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{modelo}
    public List <Papel> buscarPorModelo (String modelo) {
        return service.findByModelo(modelo);
    }

    @POST
    public Papel incluir (PapelDto dto){
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void editar(Long id, PapelDto dto){
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void excluir(Long id){
        service.delete(id);
    }
}
