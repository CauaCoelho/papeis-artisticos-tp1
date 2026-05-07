package br.unitins.tp1.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Compra extends DefaultEntity {
    private FormaDePagamento formaDePagamento;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    @ManyToOne
    @JoinColumn(name = "id_cliente") // Sempre que atributo for outra entidade, é preciso especificar a relação
    private Cliente cliente;

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

}
