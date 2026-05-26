package br.unitins.tp1.service;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.EnderecoDTOResponse;

public interface EnderecoService {

    EnderecoDTOResponse findByCep(String cep);

    EnderecoDTOResponse create(EnderecoDTO dto);

    void update(Long id, EnderecoDTO dto);

    void delete(Long id);

}
