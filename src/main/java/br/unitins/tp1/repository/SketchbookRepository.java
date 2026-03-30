package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SketchbookRepository implements PanacheRepository<Sketchbook> {

    public PanacheQuery<Sketchbook> findByNome(String nome){
        if (nome == null || nome.isEmpty()) {
            return null;
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }
    
    public List<Sketchbook> findByTextura(Textura textura){
        return find("SELECT s FROM Sketchbook s WHERE p.textura LIKE ?1", "%" + textura + "%").list();
    }
    
    public List<Sketchbook> findByCapa(Capa capa){
        return find("SELECT s FROM Sketchbook s WHERE p.capa LIKE ?1", "%" + capa + "%").list();
    }

    public List<Sketchbook> findByCategoria(Categoria categoria){
        return find("SELECT s FROM Sketchbook s WHERE p.categoria LIKE ?1", "%" + categoria + "%").list();
    }
    
    
}