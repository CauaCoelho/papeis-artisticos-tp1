package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;

public interface UsuarioService {
    List<UsuarioDTOResponse> findAll();

    UsuarioDTOResponse findByNome(String nome);

    UsuarioDTOResponse findById(Long id);

    UsuarioDTOResponse findByLogin(String login);

    UsuarioDTOResponse findByLoginAndSenha(String login, String senha);

    void update(Long id, UsuarioDTO dto);

    void delete(Long id);
}
