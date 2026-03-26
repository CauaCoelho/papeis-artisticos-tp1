package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelAvulsoDTO;
import br.unitins.tp1.dto.PapelAvulsoDTOResponse;
import br.unitins.tp1.model.PapelAvulso;
import br.unitins.tp1.model.Textura;

public interface PapelAvulsoService {
    List<PapelAvulso> findAll();
    List<PapelAvulso> findByTextura(Textura textura);
    PapelAvulsoDTOResponse findById(Long id);
    PapelAvulsoDTOResponse create (PapelAvulsoDTO dto);
    void update (Long id, PapelAvulsoDTO dto);
    void delete (Long id);
}
