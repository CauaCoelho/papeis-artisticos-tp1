package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum FormaDePagamento {
    CARTAO_CREDITO(1l, "Cartão de crédito"),
    CARTAO_DEBITO(2l, "Cartão de débito"),
    BOLETO(3l, "Boleto"),
    PIX(4l, "Pix");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("descricao")
    public final String DESCRICAO;

    FormaDePagamento(Long id, String descricao) {
        this.ID = id;
        this.DESCRICAO = descricao;
    }

    public static FormaDePagamento valueOf(Long id) {
        for (FormaDePagamento formaDePagamento : FormaDePagamento.values()) {
            if (id == formaDePagamento.ID)
                return formaDePagamento;
        }
        return null;
    }
}
