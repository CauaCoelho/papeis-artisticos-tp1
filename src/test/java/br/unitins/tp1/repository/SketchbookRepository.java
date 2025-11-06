package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SketchbookRepository implements PanacheRepository<Sketchbook> {

    public List<Sketchbook> findByNome(String nome) {
        return find("SELECT s FROM Sketchbook s WHERE s.nome LIKE ?1 ", "%"+nome+"%").list();
    }
    
    public List<Sketchbook> findByTextura(Textura textura){
        return find("SELECT s FROM Sketchbook s WHERE p.textura LIKE ?1", "%" + textura + "%").list();
    }
    

    
    
}