package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.PapelAvulso;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class PapelAvulsoRepository implements PanacheRepository<PapelAvulso>{ //padroniza os recursos de acordo com a entidade PapelAvulso

    public List<PapelAvulso> findByPapelAvulso(PapelAvulso papelavulso){
        return find("SELECT b FROM PapelAvulso b WHERE b.papelavulso LIKE ?1", "%" + papelavulso + "%").list();
    }
    
    public List<PapelAvulso> findByTextura(Textura textura){
        return find("SELECT b FROM PapelAvulso b WHERE b.papelavulso LIKE ?1", "%" + textura + "%").list();
    }
}
