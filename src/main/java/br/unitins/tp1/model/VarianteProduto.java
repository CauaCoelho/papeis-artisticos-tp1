package br.unitins.tp1.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class VarianteProduto {
    private Long id;
    private String nome;
    private int gramatura;
    private String cor;
    private double preco;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
