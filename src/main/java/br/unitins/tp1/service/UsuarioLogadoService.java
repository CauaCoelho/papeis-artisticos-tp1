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

    @jakarta.transaction.Transactional
    public Usuario getUsuarioLogado() {

        String sub = jwt.getSubject();

        if (sub == null) {
            throw new NotAuthorizedException("Token inválido");
        }

        Usuario usuario = usuarioRepository.findBySub(sub);

        if (usuario == null) {
            String username = jwt.getClaim("preferred_username");
            String email = jwt.getClaim("email");
            String name = jwt.getClaim("name");
            
            boolean isAdmin = jwt.getGroups() != null && 
                (jwt.getGroups().contains("ADMIN") || jwt.getGroups().contains("Adm") || jwt.getGroups().contains("admin"));
            
            if (isAdmin) {
                usuario = new Usuario();
                usuario.setPerfil(br.unitins.tp1.model.Perfil.ADM);
            } else {
                usuario = new br.unitins.tp1.model.Cliente();
                usuario.setPerfil(br.unitins.tp1.model.Perfil.USER);
            }
            
            usuario.setSub(sub);
            usuario.setLogin(email != null ? email : (username != null ? username : sub));
            usuario.setUsername(username != null ? username : sub);
            usuario.setNome(name != null ? name : (username != null ? username : "Usuário Keycloak"));
            
            usuarioRepository.persist(usuario);
        }

        return usuario;
    }

    public Long getIdUsuarioLogado() {
        return getUsuarioLogado().getId();
    }
}
