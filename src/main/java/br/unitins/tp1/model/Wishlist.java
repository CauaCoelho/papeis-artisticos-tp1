package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entidade que representa a lista de desejos (wishlist) de um usuário.
 * Cada item é um produto que o usuário deseja.
 */
@Entity
@Table(name = "wishlist", uniqueConstraints = {
    @UniqueConstraint(name = "uk_wishlist_usuario_produto", 
        columnNames = {"usuario_id", "produto_id"})
})
public class Wishlist extends DefaultEntity {
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Cliente usuario;
    
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public Cliente getUsuario() {
        return usuario;
    }

    public void setUsuario(Cliente usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
