package br.unitins.tp1.repository;

import br.unitins.tp1.model.Cupom;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomRepository implements PanacheRepository<Cupom> {
    
    public io.quarkus.hibernate.orm.panache.PanacheQuery<Cupom> findByCodigo(String codigo) {
        return find("UPPER(codigo) LIKE UPPER(?1) ", "%" + codigo + "%");
    }
}
