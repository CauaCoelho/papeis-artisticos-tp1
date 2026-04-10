package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.dto.SketchbookDTOResponse;
import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;

public interface SketchbookService {
    PageResponse<SketchbookDTOResponse> findAll(int page, int pageSize);
    List<Sketchbook> findByTextura(Textura textura);
    List<Sketchbook> findByCapa(Capa capa);
    List<Sketchbook> findByCategoria (Categoria categoria);
    SketchbookDTOResponse findById(Long id);
    SketchbookDTOResponse create (SketchbookDTO dto);
    void update (Long id, SketchbookDTO dto);
    void delete (Long id);
    long count();
}
