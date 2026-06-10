package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.model.Compra;
import br.unitins.tp1.model.FormaDePagamento;
import br.unitins.tp1.model.ItemPedido;

public record CompraDTOResponse(
        Long id,
        Long clienteId,
        List<ItemPedido> itens,
        FormaDePagamento formaDePagamento,
        LocalDateTime dataPedido,
        BigDecimal total) {
    public static CompraDTOResponse valueOf(Compra compra) {
        return new CompraDTOResponse(
                compra.getId(),
                compra.getCliente() != null ? compra.getCliente().getId() : null,
                compra.getItens(),
                compra.getFormaDePagamento(),
                compra.getDataPedido(),
                compra.getTotal()
        );
    }

}
