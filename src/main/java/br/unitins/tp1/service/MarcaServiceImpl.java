package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.repository.MarcaRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {
    MarcaRepository marcaRepository;

    @Inject
    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public Marca create(MarcaDTO marcaDTO) {
        Marca marca = new Marca();
        marca.setNome(marcaDTO.nome());
        marcaRepository.persist(marca);
        return marca;
    }

    public Marca findById(Long id) {
        return marcaRepository.findById(id);
    }

    public PageResponse<MarcaDTOResponse> findAll(int page, int pageSize) {
        // 1. Cria a query básica
        PanacheQuery<Marca> query = marcaRepository.findAll();

        // 2. Aplica a paginação e converte para DTO usando o método valueOf
        List<MarcaDTOResponse> list = query.page(page, pageSize)
                .stream()
                .map(MarcaDTOResponse::valueOf)
                .toList();

        // 3. Obtém o total de registros para o [length] do mat-paginator
        long total = query.count();

        return new PageResponse<>(list, total);
    }

    public void update(Long id, MarcaDTO dto) {
        Marca marca = findById(id);
        if (marca != null)
            marca.setNome(dto.nome());
    }

    public void delete(Long id) {
        marcaRepository.deleteById(id);
    }

    public long count() {
        return marcaRepository.count();
    }

    public long count(String nome) {
        return marcaRepository.findByNome(nome).count();
    }

    public PanacheQuery<Marca> findByNome(String nome) {
        return marcaRepository.findByNome(nome);
    }
}
