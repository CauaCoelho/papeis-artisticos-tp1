package br.unitins.tp1.dto;

import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;

public record PapelDTOResponse(
    Long id,
    Textura textura,
    Formato formato
) {
    public static PapelDTOResponse valueOf(Papel papel){
        return new PapelDTOResponse(
            papel.getId(),
            papel.getTextura(),
            papel.getFormato()
        );
    }

}
