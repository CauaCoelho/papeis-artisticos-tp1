package br.unitins.tp1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

//ItemPedido existe para fazer o intermédio entre Compra e Produto, para que 
//não exista um @ManyToMany em Compra, pois pode ser perigoso
@Entity
public class ItemPedido extends DefaultEntity {

    @ManyToOne
    private Compra compra;

    @ManyToOne
    private VarianteProduto variante;

    private Integer quantidade;

    private Double preco;

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public VarianteProduto getVariante() {
        return variante;
    }

    public void setVariante(VarianteProduto variante) {
        this.variante = variante;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

}