package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelDto;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;

public interface PapelService {
    List<Papel> findAll();
    List<Papel> findByTextura (Textura textura);
    Papel findById(Long id);
    Papel create (PapelDto dto);
    void update (Long id, PapelDto dto);
    void delete (Long id);
}
