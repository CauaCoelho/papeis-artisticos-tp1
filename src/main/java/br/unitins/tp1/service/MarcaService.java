package br.unitins.tp1.service;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Marca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.validation.Valid;

public interface MarcaService {
    PageResponse<MarcaDTOResponse> findAll(int page, int pageSize);

    PanacheQuery<Marca> findByNome(String nome);

    Marca findById(Long id);

    Marca create(@Valid MarcaDTO dto);

    void update(Long id, MarcaDTO dto);

    void delete(Long id);

    long count();

    long count(String nome);
}