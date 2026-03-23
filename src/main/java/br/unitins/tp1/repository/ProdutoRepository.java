package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped //tempo de vida da aplicação enquanto o server estiver no ar
public class ProdutoRepository implements PanacheRepository<Produto>{ //padroniza os recursos de acordo com a entidade Produto

    public List<Produto> findByTextura(Textura textura){
        return find("SELECT p FROM Produto p WHERE p.textura LIKE ?1", "%" + textura + "%").list();
    }
}
