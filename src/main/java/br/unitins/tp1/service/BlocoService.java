package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Textura;
import jakarta.validation.Valid;

public interface BlocoService {
    PageResponse<BlocoDTOResponse> findAll(int page, int pageSize);
    List<Bloco> findByTextura(Textura textura);
    List<Bloco> findByQuantidadeFolhas (int quantidadeFolhas);
    List<Bloco> findByCategoria (Categoria categoria);
    Bloco findById(Long id);
    Bloco create (@Valid BlocoDTO blocoDTO);
    void update (Long id, BlocoDTO blocoDTO);
    void delete (Long id);
    long count();
    long count(String nome);
}
