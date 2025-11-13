package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelDTO;
import br.unitins.tp1.dto.PapelDTOResponse;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;

public interface PapelService {
    List<Papel> findAll();
    List<Papel> findByTextura (Textura textura);
    List<Papel> findByFormato (Formato formato);
    PapelDTOResponse findById(Long id);
    PapelDTOResponse create (PapelDTO dto);
    void update (Long id, PapelDTO dto);
    void delete (Long id);
}
