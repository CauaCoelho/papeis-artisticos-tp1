package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.dto.SketchbookDTOResponse;
import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import jakarta.validation.Valid;

public interface SketchbookService {
    PageResponse<SketchbookDTOResponse> findAll(int page, int pageSize);
    List<Sketchbook> findByTextura(Textura textura);
    List<Sketchbook> findByCapa(Capa capa);
    List<Sketchbook> findByCategoria (Categoria categoria);
    Sketchbook findById(Long id);
    Sketchbook create (@Valid SketchbookDTO sketchbookDTO);
    void update (Long id, SketchbookDTO sketchbookDTO);
    void delete (Long id);
    long count();
}
