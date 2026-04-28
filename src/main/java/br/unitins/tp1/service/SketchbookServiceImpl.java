package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.dto.SketchbookDTOResponse;
import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.CategoriaRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.SketchbookRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SketchbookServiceImpl implements SketchbookService {

    @Inject
    SketchbookRepository repository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    CategoriaRepository categoriaRepository;

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
    public Sketchbook findById(Long id) {
        Sketchbook sketchbook = repository.findById(id);

        if (sketchbook == null)
            return null;
        return sketchbook;
    }

    @Override
    public List<Sketchbook> findByCategoria(Long idCategoria) {
        return repository.findByCategoria(idCategoria);
    }

    @Override
    public List<Sketchbook> findByMarca(Long idMarca) {
        return repository.findByMarca(idMarca);
    }

    @Override
    public Sketchbook create(SketchbookDTO dto) {
        Sketchbook sketchbook = new Sketchbook();
        sketchbook.setTextura(Textura.valueOf(dto.idTextura()));
        sketchbook.setCapa(Capa.valueOf(dto.idCapa()));
        sketchbook.setQuantidadeFolhas(dto.quantidadeFolhas());

        if (dto.idMarca() != null) {
            sketchbook.setMarca(marcaRepository.findById(dto.idMarca()));
        }

        if (dto.idCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.idCategoria());
            if (categoria != null) {
                List<Categoria> categorias = new java.util.ArrayList<>();
                categorias.add(categoria);
                sketchbook.setCategorias(categorias);
            }
        }

        repository.persist(sketchbook); // manter os dados no BD
        return sketchbook;
    }

    @Override
    public void update(Long id, SketchbookDTO dto) {
        Sketchbook sketchbook = repository.findById(id);
        sketchbook.setTextura(Textura.valueOf(dto.idTextura()));
        sketchbook.setCapa(Capa.valueOf(dto.idCapa()));
        sketchbook.setQuantidadeFolhas(dto.quantidadeFolhas());

        if (dto.idMarca() != null) {
            sketchbook.setMarca(marcaRepository.findById(dto.idMarca()));
        } else {
            sketchbook.setMarca(null);
        }

        if (dto.idCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.idCategoria());
            if (categoria != null) {
                List<Categoria> categorias = new java.util.ArrayList<>();
                categorias.add(categoria);
                sketchbook.setCategorias(categorias);
            }
        } else {
            sketchbook.setCategorias(null);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return repository.findByNome(nome).count();
    }

}
