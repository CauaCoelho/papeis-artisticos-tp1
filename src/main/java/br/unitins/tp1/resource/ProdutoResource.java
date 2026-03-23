package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.dto.ProdutoDTOResponse;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.ProdutoService;
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
public class ProdutoResource {


    @Inject //injeção de dependência
    ProdutoService service;

    @GET
    public List<Produto> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path ("/find/{textura}") //Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public List <Produto> buscarPorTextura (Textura textura) {
        return service.findByTextura(textura);
    }

    @RolesAllowed("Administrador")
    @POST
    public ProdutoDTOResponse incluir (ProdutoDTO dto){
        return service.create(dto);
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("/{id}")
    @Transactional
    public void editar(Long id, ProdutoDTO dto){
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
