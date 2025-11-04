package br.unitins.tp1.dto;

import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Textura;

public record SketchbookDTO(
    Capa capa,
    Integer quantidadeFolhas,
    Textura textura,
    Formato formato) {
    
}
