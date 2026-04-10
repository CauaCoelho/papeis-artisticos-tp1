package br.unitins.tp1.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> data,
    long totalRecords
) {}
