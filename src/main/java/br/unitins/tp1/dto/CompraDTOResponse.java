package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.Compra;
import br.unitins.tp1.model.FormaDePagamento;
import br.unitins.tp1.model.ItemPedido;

public record CompraDTOResponse(
        Long clienteId,
        List<ItemPedido> itens,
        FormaDePagamento formaDePagamento) {
    public static CompraDTOResponse valueOf(Compra compra) {
        return new CompraDTOResponse(
                compra.getCliente() != null ? compra.getCliente().getId() : null,
                compra.getItens(),
                compra.getFormaDePagamento()
        );
    }

}
