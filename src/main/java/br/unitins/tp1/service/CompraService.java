package br.unitins.tp1.service;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraDTOResponse;
import br.unitins.tp1.dto.PageResponse;

public interface CompraService {
    public void create(CompraDTO dto);

    public PageResponse<CompraDTOResponse> findAll(int page, int pageSize);
}
