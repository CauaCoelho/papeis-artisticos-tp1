package br.unitins.tp1.dto;

import java.util.List;


import br.unitins.tp1.model.EspecificacaoTecnica;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;

public record ProdutoDTOResponse(
    Long id,
    Textura textura,
    Marca marca,
    EspecificacaoTecnica especificacaoTecnica,
    String nomeImagem
) {
    public static ProdutoDTOResponse valueOf(Produto produto){
        if (produto == null)
            return null;
        return new ProdutoDTOResponse(
            produto.getId(),
            produto.getTextura(),
            produto.getMarca(),
            produto.getEspecificacaoTecnica(),
            produto.getNomeImagem()
        );
    }
}
