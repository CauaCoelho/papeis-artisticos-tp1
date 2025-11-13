package br.unitins.tp1.dto;

import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;

public record SketchbookDTOResponse(
    Long id,
    Capa capa,
    Integer quantidadeFolhas,
    Textura textura,
    Formato formato) {

    public static SketchbookDTOResponse valueOf(Sketchbook sketchbook){
        return new SketchbookDTOResponse(
            sketchbook.getId(),
            sketchbook.getCapa(),
            sketchbook.getQuantidadeFolhas(),
            sketchbook.getTextura(),
            sketchbook.getFormato());
    }
    
}
