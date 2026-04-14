package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.dto.SketchbookDTOResponse;
import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.SketchbookRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SketchbookServiceImpl implements SketchbookService{

    @Inject
    SketchbookRepository repository;

    @Override
     public PageResponse<SketchbookDTOResponse> findAll(int page, int pageSize) {
        // 1. Cria a query básica
        PanacheQuery<Sketchbook> query = repository.findAll();
        
        // 2. Aplica a paginação e converte para DTO usando o método valueOf
        List<SketchbookDTOResponse> list = query.page(page, pageSize)
            .stream()
            .map(SketchbookDTOResponse::valueOf)
            .toList();
            
        // 3. Obtém o total de registros para o [length] do mat-paginator
        long total = query.count();
        
        return new PageResponse<>(list, total);
    }


    @Override
    public List<Sketchbook> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
    }

    @Override
    public List<Sketchbook> findByCapa(Capa capa) {
        return repository.findByCapa(capa);
    }
    

    @Override
    public SketchbookDTOResponse findById(Long id) {
        Sketchbook sketchbook = repository.findById(id);
       
       if (sketchbook == null) 
        return null;
       return SketchbookDTOResponse.valueOf(sketchbook);
    }

    @Override
    public List<Sketchbook> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    } 

    @Override
    public SketchbookDTOResponse create(SketchbookDTO dto) {
        Sketchbook sketchbook = new Sketchbook();
        sketchbook.setTextura(dto.textura());
        sketchbook.setCapa(dto.capa());
        sketchbook.setQuantidadeFolhas(dto.quantidadeFolhas());
        repository.persist(sketchbook); //manter os dados no BD
        return SketchbookDTOResponse.valueOf(sketchbook);
    }

    @Override
    public void update(Long id, SketchbookDTO dto) {
        Sketchbook sketchbook = repository.findById(id);
        sketchbook.setTextura(dto.textura());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    @Override
    public long count() {
        return repository.count();
    }

    


    
}
