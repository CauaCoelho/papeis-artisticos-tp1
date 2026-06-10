package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CupomDTO(
    String codigo,
    BigDecimal valor,
    LocalDateTime validade
) { }
