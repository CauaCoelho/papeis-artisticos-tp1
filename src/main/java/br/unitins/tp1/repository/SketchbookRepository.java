package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SketchbookRepository implements PanacheRepository<Sketchbook> {

    public PanacheQuery<Sketchbook> findByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public List<Sketchbook> findByTextura(Textura textura) {
        return find("SELECT s FROM Sketchbook s WHERE s.textura = ?1", textura).list();
    }

    public List<Sketchbook> findByCapa(Capa capa) {
        return find("SELECT s FROM Sketchbook s WHERE s.capa = ?1", capa).list();
    }

    public List<Sketchbook> findByCategoria(Long idCategoria) {
        return find("SELECT s FROM Sketchbook s JOIN s.categorias c WHERE c.id = ?1", idCategoria).list();
    }

    public List<Sketchbook> findByMarca(Long idMarca) {
        return find("SELECT s FROM Sketchbook s WHERE s.marca.id = ?1", idMarca).list();
    }

}