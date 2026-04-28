package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.EspecificacaoTecnica;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;

public record SketchbookDTOResponse(
        Long id,
        Capa capa,
        Integer quantidadeFolhas,
        Textura textura,
        EspecificacaoTecnica especificacaoTecnica,
        Marca marca,
        List<Categoria> categorias) {

    public static SketchbookDTOResponse valueOf(Sketchbook sketchbook) {
        return new SketchbookDTOResponse(
                sketchbook.getId(),
                sketchbook.getCapa(),
                sketchbook.getQuantidadeFolhas(),
                sketchbook.getTextura(),
                sketchbook.getEspecificacaoTecnica(),
                sketchbook.getMarca(),
                sketchbook.getCategorias());
    }

}
