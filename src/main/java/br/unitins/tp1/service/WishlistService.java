package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.WishlistDTO;
import br.unitins.tp1.dto.WishlistDTOResponse;

public interface WishlistService {
    
    /**
     * Adiciona um produto à wishlist do usuário.
     * @param usuarioId ID do usuário
     * @param dto DTO contendo o ID do produto
     * @return DTO da wishlist criada
     * @throws IllegalArgumentException se o produto já está na wishlist
     */
    WishlistDTOResponse adicionar(Long usuarioId, WishlistDTO dto);
    
    /**
     * Lista todos os itens da wishlist do usuário.
     * @param usuarioId ID do usuário
     * @return Lista de DTOs da wishlist
     */
    List<WishlistDTOResponse> listar(Long usuarioId);
    
    /**
     * Remove um produto da wishlist do usuário.
     * @param usuarioId ID do usuário
     * @param produtoId ID do produto
     */
    void remover(Long usuarioId, Long produtoId);
}
