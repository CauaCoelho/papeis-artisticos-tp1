package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.dto.UsuarioDTOResponse;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    public List<UsuarioDTOResponse> findAll() {
        List<Usuario> list = repository.listAll();
        return list.stream().map(UsuarioDTOResponse::valueOf).toList();
    }

    @Override
    public UsuarioDTOResponse findById(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null)
            return null;
        return UsuarioDTOResponse.valueOf(usuario);
    }

    @Override
    public UsuarioDTOResponse findByNome(String nome) {
        Usuario usuario = repository.findByNome(nome);
        if (usuario == null)
            return null;
        return UsuarioDTOResponse.valueOf(usuario);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Long id, UsuarioDTO dto) {
    }

    @Override
    public UsuarioDTOResponse findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null)
            return null;
        return UsuarioDTOResponse.valueOf(usuario);
    }

    @Override
    public UsuarioDTOResponse findByLoginAndSenha(String login, String senha) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByLoginAndSenha'");
    }
    
    @Override
    public UsuarioDTOResponse findBySub(String sub) {
        Usuario usuario = repository.findBySub(sub);
        if (usuario == null)
            return null;
        return UsuarioDTOResponse.valueOf(usuario);
    }

}
