package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Textura;
import jakarta.ws.rs.core.Response;

public interface BlocoService {
    List<Bloco> findAll();
    Response findByNome(String nome);
    List<Bloco> findByTextura(Textura textura);
    List<Bloco> findByQuantidadeFolhas (int quantidadeFolhas);
    List<Bloco> findByCategoria (Categoria categoria);
    BlocoDTOResponse findById(Long id);
    BlocoDTOResponse create (BlocoDTO dto);
    void update (Long id, BlocoDTO dto);
    void delete (Long id);
    long count();
}
