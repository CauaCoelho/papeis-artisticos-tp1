package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDto;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;

public interface BlocoService {
    List<Bloco> findAll();
    List<Bloco> findByTextura(Textura textura);
    Bloco findById(Long id);
    Bloco create (BlocoDto dto);
    void update (Long id, BlocoDto dto);
    void delete (Long id);
}
