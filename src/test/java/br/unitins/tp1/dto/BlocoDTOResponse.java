package br.unitins.tp1.dto;

import br.unitins.tp1.model.Bloco;

public record BlocoDTOResponse(
    Integer quantidadeFolhas
) {
    public static BlocoDTOResponse valueOf(Bloco bloco){
        return new BlocoDTOResponse(
            bloco.getQuantidadeFolhas()
        );
    }

}
