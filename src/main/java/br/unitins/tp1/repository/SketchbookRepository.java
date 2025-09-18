package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Sketchbook;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SketchbookRepository implements PanacheRepository<Sketchbook> {

    public List<Sketchbook> findByNome(String nome) {
        return find("SELECT s FROM Sketchbook s WHERE s.nome LIKE ?1 ", "%"+nome+"%").list();
    }
    
}