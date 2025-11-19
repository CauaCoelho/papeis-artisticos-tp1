package br.unitins.tp1.dto;

import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;

public record RoloDTOResponse(
    Long id,
    Textura textura,
    Formato formato
) {
    public static RoloDTOResponse valueOf(Rolo rolo){
        return new RoloDTOResponse(
            rolo.getId(),
            rolo.getTextura(),
            rolo.getFormato()
        );
    }

}
