package br.unitins.tp1.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Compra extends DefaultEntity {
    private FormaDePagamento formaDePagamento;
    
    private LocalDateTime dataPedido;
    
    private BigDecimal total;
    
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_cupom")
    private Cupom cupomAplicado;

    @ManyToOne
    @JoinColumn(name = "id_endereco_entrega")
    private Endereco enderecoEntrega;
    
    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cupom getCupomAplicado() {
        return cupomAplicado;
    }

    public void setCupomAplicado(Cupom cupomAplicado) {
        this.cupomAplicado = cupomAplicado;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
