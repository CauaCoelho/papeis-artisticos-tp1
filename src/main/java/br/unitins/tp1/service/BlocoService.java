package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;

public interface BlocoService {
    List<Bloco> findAll();
    List<Bloco> findByTextura(Textura textura);
    BlocoDTOResponse findById(Long id);
    BlocoDTOResponse create (BlocoDTO dto);
    void update (Long id, BlocoDTO dto);
    void delete (Long id);
}
