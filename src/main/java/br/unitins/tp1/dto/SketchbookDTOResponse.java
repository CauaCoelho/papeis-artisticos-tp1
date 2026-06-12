package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Capa;

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
        MarcaDTOResponse marca,
        List<ArquivoResponseDTO> imagens) {

    public static SketchbookDTOResponse valueOf(Sketchbook sketchbook) {
        return new SketchbookDTOResponse(
                sketchbook.getId(),
                sketchbook.getCapa(),
                sketchbook.getQuantidadeFolhas(),
                sketchbook.getTextura(),
                sketchbook.getEspecificacaoTecnica(),
                sketchbook.getMarca() == null ? null : MarcaDTOResponse.valueOf(sketchbook.getMarca()),
                sketchbook.getArquivos() == null ? java.util.Collections.emptyList() : sketchbook.getArquivos().stream().map(ArquivoResponseDTO::valueOf).toList());
    }
}
