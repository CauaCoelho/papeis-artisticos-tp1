package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Papel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class PapelRepository implements PanacheRepository<Papel>{ //padroniza os recursos de acordo com a entidade Papel

    public List<Papel> findByModelo(String modelo){
        return find("SELECT p FROM Papel p WHERE p.modelo LIKE ?1", "%" + modelo + "%").list();
    }
    
}
