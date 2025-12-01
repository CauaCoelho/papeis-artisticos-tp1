package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.model.Textura;

public interface UsuarioService {
    List<Usuario> findAll();
    List<Usuario> findByTextura (Textura textura);
    List<Usuario> findByFormato (Formato formato);
    UsuarioDTOResponse findById(Long id);
    UsuarioDTOResponse create (UsuarioDTO dto);
    void update (Long id, UsuarioDTO dto);
    void delete (Long id);
}
