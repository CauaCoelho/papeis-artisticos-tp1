package br.unitins.tp1.dto;

import br.unitins.tp1.model.Capa;

public record SketchbookDto(
    Capa capa,
    Integer quantidadeFolhas) {
    
}
