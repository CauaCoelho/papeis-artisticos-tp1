package br.unitins.tp1.dto;

import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;

public record RoloDTOResponse(
    Long id,
    Textura textura,
    Double comprimento,
    java.util.List<ArquivoResponseDTO> imagens
) {
    public static RoloDTOResponse valueOf(Rolo rolo){
        return new RoloDTOResponse(
            rolo.getId(),
            rolo.getTextura(),
            rolo.getComprimento(),
            rolo.getArquivos() == null ? java.util.Collections.emptyList() : rolo.getArquivos().stream().map(ArquivoResponseDTO::valueOf).toList()
        );
    }
}
