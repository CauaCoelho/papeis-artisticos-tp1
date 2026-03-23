package br.unitins.tp1.dto;

import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;

public record ProdutoDTOResponse(
    Long id,
    Textura textura
) {
    public static ProdutoDTOResponse valueOf(Produto produto){
        return new ProdutoDTOResponse(
            produto.getId(),
            produto.getTextura()
        );
    }

}
