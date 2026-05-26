package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;

public interface UsuarioService {
    List<UsuarioDTOResponse> findAll();

    UsuarioDTOResponse findByNome(String nome);

    UsuarioDTOResponse findById(Long id);

    UsuarioDTOResponse findByLogin(String login);
    
    /**
     * Busca usuário pelo 'sub' (Subject) do Keycloak.
     * Este é o ID único do usuário no Keycloak.
     * @param sub ID do usuário no Keycloak
     * @return DTO do usuário ou null se não encontrado
     */
    UsuarioDTOResponse findBySub(String sub);

    UsuarioDTOResponse findByLoginAndSenha(String login, String senha);

    void update(Long id, UsuarioDTO dto);

    void delete(Long id);
}
