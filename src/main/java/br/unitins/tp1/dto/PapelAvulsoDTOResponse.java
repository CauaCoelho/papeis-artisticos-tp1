package br.unitins.tp1.dto;

import br.unitins.tp1.model.PapelAvulso;
import br.unitins.tp1.model.Textura;

public record PapelAvulsoDTOResponse(
    Long id,
    String tipoPapel,
    String tamanho,
    Textura textura
) {
    public static PapelAvulsoDTOResponse valueOf(PapelAvulso papelavulso){
        return new PapelAvulsoDTOResponse(
            papelavulso.getId(),
            papelavulso.getTipoPapel(),
            papelavulso.getTamanho(),
            papelavulso.getTextura()
        );
    }

}
