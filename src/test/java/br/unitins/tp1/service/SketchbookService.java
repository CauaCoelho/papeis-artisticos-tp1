package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;

public interface SketchbookService {
    List<Sketchbook> findAll();
    List<Sketchbook> findByTextura(Textura textura);
    Sketchbook findById(Long id);
    Sketchbook create (SketchbookDTO dto);
    void update (Long id, SketchbookDTO dto);
    void delete (Long id);
}
