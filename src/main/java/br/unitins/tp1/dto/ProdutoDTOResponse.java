package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.model.EspecificacaoTecnica;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;

public record ProdutoDTOResponse(
        Long id,
        String nome,
        BigDecimal preco,
        int estoque,
        Textura textura,
        MarcaDTOResponse marca,
        EspecificacaoTecnica especificacaoTecnica,
        List<ArquivoResponseDTO> imagens) {
    public static ProdutoDTOResponse valueOf(Produto produto) {
        if (produto == null)
            return null;
        return new ProdutoDTOResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getTextura(),
                produto.getMarca() == null ? null : MarcaDTOResponse.valueOf(produto.getMarca()),
                produto.getEspecificacaoTecnica(),
                produto.getArquivos() == null ? java.util.Collections.emptyList()
                        : produto.getArquivos().stream().map(ArquivoResponseDTO::valueOf).toList());
    }
}
