package br.unitins.tp1.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @Column(nullable = false)
    private LocalDateTime validade;
    
    private BigDecimal valor;
    @ManyToMany
    private List<Produto> produtosPermitidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public void setValidade(LocalDateTime validade) {
        this.validade = validade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<Produto> getProdutosPermitidos() {
        return produtosPermitidos;
    }

    public void setProdutosPermitidos(List<Produto> produtosPermitidos) {
        this.produtosPermitidos = produtosPermitidos;
    }
}