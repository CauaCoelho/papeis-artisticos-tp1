package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Compra extends DefaultEntity {
    private FormaDePagamento formaDePagamento;
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

}
