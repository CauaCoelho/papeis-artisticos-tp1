package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class PapelRepository implements PanacheRepository<Papel>{ //padroniza os recursos de acordo com a entidade Papel

    public List<Papel> findByTextura(Textura textura){
        return find("SELECT p FROM Papel p WHERE p.textura LIKE ?1", "%" + textura + "%").list();
    }
        public List<Papel> findByFormato(Formato formato){
        return find("SELECT p FROM Papel p WHERE p.formato LIKE ?1", "%" + formato + "%").list();
    }
}
