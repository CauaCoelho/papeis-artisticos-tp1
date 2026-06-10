package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Wishlist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WishlistRepository implements PanacheRepository<Wishlist> {
    
    /**
     * Busca todos os itens da wishlist de um usuário.
     * @param usuarioId ID do usuário
     * @return Lista de itens da wishlist
     */
    public List<Wishlist> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }
    
    /**
     * Verifica se um produto já está na wishlist do usuário.
     * @param usuarioId ID do usuário
     * @param produtoId ID do produto
     * @return true se o produto está na wishlist, false caso contrário
     */
    public boolean existsByUsuarioAndProduto(Long usuarioId, Long produtoId) {
        return count("usuario.id = ?1 AND produto.id = ?2", usuarioId, produtoId) > 0;
    }
    
    /**
     * Remove um item da wishlist by usuário e produto.
     * @param usuarioId ID do usuário
     * @param produtoId ID do produto
     * @return número de registros deletados
     */
    public long deleteByUsuarioAndProduto(Long usuarioId, Long produtoId) {
        return delete("usuario.id = ?1 AND produto.id = ?2", usuarioId, produtoId);
    }
}
