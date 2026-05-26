package br.unitins.tp1.repository;

import br.unitins.tp1.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // tempo de vida da aplicação enquanto o server estiver no ar
public class UsuarioRepository implements PanacheRepository<Usuario> { // padroniza os recursos de acordo com a entidade
                                                                       // Usuario

    public Usuario findByLogin(String login) {
        return find("SELECT u FROM Usuario u WHERE u.login = ?1", login).firstResult();
    }

    public Usuario findByNome(String nome) {
        return find("SELECT u FROM Usuario u WHERE u.nome LIKE ?1", "%" + nome + "%").firstResult();
    }
    
    /**
     * Busca usuário pelo 'sub' (Subject do Keycloak).
     * Este é o ID único do usuário no Keycloak.
     * @param sub ID do usuário no Keycloak
     * @return Usuario encontrado ou null
     */
    public Usuario findBySub(String sub) {
        return find("SELECT u FROM Usuario u WHERE u.sub = ?1", sub).firstResult();
    }
}
