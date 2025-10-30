package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Bloco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class BlocoRepository implements PanacheRepository<Bloco>{ //padroniza os recursos de acordo com a entidade Bloco

    public List<Bloco> findByBloco(Bloco bloco){
        return find("SELECT p FROM Bloco p WHERE p.bloco LIKE ?1", "%" + bloco + "%").list();
    }
    
}
