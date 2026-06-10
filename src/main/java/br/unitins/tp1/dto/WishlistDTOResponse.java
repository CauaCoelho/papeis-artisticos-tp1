package br.unitins.tp1.dto;

import br.unitins.tp1.model.Wishlist;

/**
 * DTO para resposta de wishlist.
 */
public record WishlistDTOResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        String produtoImagem) {
    
    public static WishlistDTOResponse valueOf(Wishlist wishlist) {
        if (wishlist == null)
            return null;
        
        String imagemUrl = wishlist.getProduto().getArquivos() != null 
            && !wishlist.getProduto().getArquivos().isEmpty()
            ? wishlist.getProduto().getArquivos().get(0).getFid()
            : null;
        
        return new WishlistDTOResponse(
                wishlist.getId(),
                wishlist.getProduto().getId(),
                wishlist.getProduto().getNome(),
                imagemUrl);
    }
}
