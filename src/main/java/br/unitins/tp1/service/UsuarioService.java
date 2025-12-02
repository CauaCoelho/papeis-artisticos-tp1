package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.model.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findByNome(String nome);
    UsuarioDTOResponse findById(Long id);
    Usuario findByLogin(String login);
    void update (Long id, UsuarioDTO dto);
    void delete (Long id);
}
