package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.EnderecoDTOResponse;

public interface EnderecoService {
    EnderecoDTOResponse findByCep(String cep);
    EnderecoDTOResponse create(EnderecoDTO dto, Long usuarioId);
    void update(Long id, EnderecoDTO dto);
    List<EnderecoDTOResponse> findByUsuario(Long usuarioId);
    void delete(Long id);
}
