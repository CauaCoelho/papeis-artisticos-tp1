package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;

public interface BlocoService {
    List<Bloco> findAll();
    List<Bloco> findByTextura(Textura textura);
    Bloco findById(Long id);
    Bloco create (BlocoDTO dto);
    void update (Long id, BlocoDTO dto);
    void delete (Long id);
}
