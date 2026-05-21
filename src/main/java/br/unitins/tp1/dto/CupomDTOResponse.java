package br.unitins.tp1.dto;

import java.time.LocalDateTime;
import br.unitins.tp1.model.Cupom;

public record CupomDTOResponse(
    Long id,
    String codigo,
    Double valor,
    LocalDateTime validade
) {
    public static CupomDTOResponse valueOf(Cupom cupom) {
        return new CupomDTOResponse(
            cupom.getId(),
            cupom.getCodigo(),
            cupom.getValor(),
            cupom.getValidade()
        );
    }
}
