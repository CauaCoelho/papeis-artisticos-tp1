package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService{

    @Inject
    UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
        return repository.listAll();
    }

    @Override
    public UsuarioDTOResponse findById(Long id) {
        Usuario usuario = repository.findById(id);
       
       if (usuario == null) 
        return null;
       return UsuarioDTOResponse.valueOf(usuario);
    }

    @Override
    public Usuario findByNome(String nome) {
        return repository.findByNome(nome);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, UsuarioDTO dto) {
    }

    @Override
    public Usuario findByLogin(String login) {
        return repository.findByLogin(login);
    }
    
}
