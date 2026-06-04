package br.unitins.tp1.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class UsuarioLogadoService {
    @Inject
    JsonWebToken jwt;
    @Inject
    UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {

        String sub = jwt.getSubject();

        if (sub == null) {
            throw new NotAuthorizedException("Token inválido");

        }

        Usuario usuario = usuarioRepository.findBySub(sub);

        if (usuario == null) {
            throw new NotAuthorizedException("Usuário não encontrado");
        }

        return usuario;
    }

    public Long getIdUsuarioLogado() {
        return getUsuarioLogado().getId();
    }
}
