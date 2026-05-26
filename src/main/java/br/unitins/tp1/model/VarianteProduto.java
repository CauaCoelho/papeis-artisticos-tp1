package br.unitins.tp1.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VarianteProduto extends DefaultEntity {
    private String formato;
    private int gramatura;
    private String cor;
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getGramatura() {
        return gramatura;
    }

    public void setGramatura(int gramatura) {
        this.gramatura = gramatura;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}
