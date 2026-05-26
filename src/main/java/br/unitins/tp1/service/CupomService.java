package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.CupomDTO;
import br.unitins.tp1.dto.CupomDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Cupom;
import jakarta.validation.Valid;

public interface CupomService {
    PageResponse<CupomDTOResponse> findAll(int page, int pageSize);
    
    Cupom findById(Long id);
    
    List<Cupom> findByCodigo(String codigo);
    
    Cupom create(@Valid CupomDTO cupomDTO);
    
    void update(Long id, CupomDTO cupomDTO);
    
    void delete(Long id);
    
    long count();
    
    long count(String codigo);
}
