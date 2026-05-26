package br.unitins.tp1.repository;

import br.unitins.tp1.model.Compra;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {
    public PanacheQuery<Compra> findByClienteId(Long id) {
        return find("cliente.id = ?1", id);
    }

    public PanacheQuery<Compra> findAll(int page, int pageSize) {
        return findAll().page(page, pageSize);
    }

}
