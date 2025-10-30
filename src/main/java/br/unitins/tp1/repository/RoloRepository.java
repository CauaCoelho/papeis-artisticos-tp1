package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Rolo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class RoloRepository implements PanacheRepository<Rolo>{ //padroniza os recursos de acordo com a entidade Rolo

    public List<Rolo> findByRolo(Rolo rolo){
        return find("SELECT p FROM Rolo p WHERE p.rolo LIKE ?1", "%" + rolo + "%").list();
    }
    
}
