package br.unitins.tp1.dto;

import java.time.LocalDateTime;

public record CupomDTO(
    String codigo,
    Double valor,
    LocalDateTime validade
) { }
