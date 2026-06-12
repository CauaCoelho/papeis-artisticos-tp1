package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.dto.ProdutoDTOResponse;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;

public interface ProdutoService {
    List<ProdutoDTOResponse> findAll();
    List<ProdutoDTOResponse> findByTextura (Textura textura);
    ProdutoDTOResponse findById(Long id);
    ProdutoDTOResponse create (ProdutoDTO dto);
    void update (Long id, ProdutoDTO dto);
    void delete (Long id);
}
