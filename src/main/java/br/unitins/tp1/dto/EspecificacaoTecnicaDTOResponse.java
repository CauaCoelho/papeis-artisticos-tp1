package br.unitins.tp1.dto;

import br.unitins.tp1.model.EspecificacaoTecnica;

public record EspecificacaoTecnicaDTOResponse(
    Double ph,
    Double opacidade,
    Double absorcao
) {
    public static EspecificacaoTecnicaDTOResponse valueOf(EspecificacaoTecnica especificacaoTecnica) {
        return new EspecificacaoTecnicaDTOResponse(
            especificacaoTecnica.getPh(),
            especificacaoTecnica.getOpacidade(),
            especificacaoTecnica.getAbsorcao()
        );
    }
}
