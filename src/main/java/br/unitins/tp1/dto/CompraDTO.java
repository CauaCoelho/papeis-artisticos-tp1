package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.FormaDePagamento;

public record CompraDTO(
        Long clienteId,
        List<ItemPedidoDTO> itens,
        FormaDePagamento formaDePagamento) {

}
