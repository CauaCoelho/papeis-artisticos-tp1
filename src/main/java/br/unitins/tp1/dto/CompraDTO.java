package br.unitins.tp1.dto;

import java.util.List;

import br.unitins.tp1.model.FormaDePagamento;

/**
 * DTO para requisição de compra.
 * Contém os dados necessários para criar uma nova compra.
 */
public record CompraDTO(
        List<ItemPedidoDTO> itens,
        FormaDePagamento formaDePagamento,
        Long cupomId) {  // Cupom é opcional

}
