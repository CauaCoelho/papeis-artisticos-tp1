package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.model.Papel;
import br.unitins.tp1.repository.PapelRepository;
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
    PapelRepository repository;

    @GET
    public List<Papel> buscarTodos() {
        return repository.listAll();
    }

    @GET
    @Path ("/find/{modelo}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{modelo}
    public List <Papel> buscarPorModelo (String modelo) {
        return repository.findByModelo(modelo);
    }

    @POST
    @Transactional //Commit
    public Papel incluir (Papel papel){
        Papel novoPapel = new Papel();
        novoPapel.setModelo(papel.getModelo());
        repository.persist(novoPapel); //manter os dados no BD
        return novoPapel;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void editar(Long id, Papel papel){
        Papel edicaoPapel = repository.findById(id);
        edicaoPapel.setModelo(papel.getModelo());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void excluir(Long id){
        repository.deleteById(id);
    }
}
