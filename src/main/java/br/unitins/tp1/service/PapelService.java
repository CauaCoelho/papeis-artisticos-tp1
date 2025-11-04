package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelDTO;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;

public interface PapelService {
    List<Papel> findAll();
    List<Papel> findByTextura (Textura textura);
    Papel findById(Long id);
    Papel create (PapelDTO dto);
    void update (Long id, PapelDTO dto);
    void delete (Long id);
}
