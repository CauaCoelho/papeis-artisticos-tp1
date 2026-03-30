package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class RoloRepository implements PanacheRepository<Rolo>{ //padroniza os recursos de acordo com a entidade Rolo

    public List<Rolo> findByRolo(Rolo rolo){
        return find("SELECT r FROM Rolo r WHERE r.rolo LIKE ?1", "%" + rolo + "%").list();
    }
    
    public List<Rolo> findByTextura(Textura textura){
        return find("SELECT r FROM Rolo r WHERE r.rolo LIKE ?1", "%" + textura + "%").list();
    }

    public List<Rolo> findByComprimento(double comprimento){
        return find("SELECT r FROM Rolo r WHERE r.comprimento = ?1", comprimento).list();
    }

    public List<Rolo> findByCategoria(Categoria categoria){
        return find("SELECT s FROM Rolo s WHERE p.categoria LIKE ?1", "%" + categoria + "%").list();
    }
}
