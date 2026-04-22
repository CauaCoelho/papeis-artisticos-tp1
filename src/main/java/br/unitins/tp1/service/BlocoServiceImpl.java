package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.BlocoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BlocoServiceImpl implements BlocoService {

    @Inject
    BlocoRepository repository;

      @Override
     public PageResponse<BlocoDTOResponse> findAll(int page, int pageSize) {
        // 1. Cria a query básica
        PanacheQuery<Bloco> query = repository.findAll();
        
        // 2. Aplica a paginação e converte para DTO usando o método valueOf
        List<BlocoDTOResponse> list = query.page(page, pageSize)
            .stream()
            .map(BlocoDTOResponse::valueOf)
            .toList();
            
        // 3. Obtém o total de registros para o [length] do mat-paginator
        long total = query.count();
        
        return new PageResponse<>(list, total);
    }

    @Override
    public List<Bloco> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
    }

    @Override
    public List<Bloco> findByQuantidadeFolhas(int quantidadeFolhas) {
        return repository.findByQuantidadeFolhas(quantidadeFolhas);
    }

    @Override
    public Bloco findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bloco> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }

    @Override
    public Bloco create(BlocoDTO dto) {
        Bloco bloco = new Bloco();
        bloco.setQuantidadeFolhas(dto.quantidadeFolhas());
        bloco.setTextura(Textura.valueOf(dto.idTextura()));
        repository.persist(bloco); // manter os dados no BD
        return bloco;
    }

    @Override
    public void update(Long id, BlocoDTO dto) {
        Bloco bloco = repository.findById(id);
        bloco.setQuantidadeFolhas(dto.quantidadeFolhas());
        bloco.setTextura(Textura.valueOf(dto.idTextura()));

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long count(String nome) {
        return repository.findByNome(nome).count();
    }

}
