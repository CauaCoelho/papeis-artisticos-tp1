package br.unitins.tp1.repository;

import br.unitins.tp1.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class UsuarioRepository implements PanacheRepository<Usuario>{ //padroniza os recursos de acordo com a entidade Usuario

    public Usuario findByLogin(String login){
        return find("SELECT u FROM Usuario u WHERE u.usuario LIKE ?1" + login).firstResult();
    }
    
    public Usuario findByNome(String nome){
        return find("SELECT u FROM Usuario u WHERE u.usuario LIKE ?1", "%" + nome + "%").firstResult();
    }
}
