package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.dto.ProdutoDTOResponse;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.model.VarianteProduto;
import br.unitins.tp1.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository repository;

    @Override
    public List<ProdutoDTOResponse> findAll() {
        return repository.listAll().stream().map(ProdutoDTOResponse::valueOf).toList();
    }

    @Override
    public List<ProdutoDTOResponse> findByTextura(Textura textura) {
        return repository.findByTextura(textura).stream().map(ProdutoDTOResponse::valueOf).toList();
    }

    @Override
    public ProdutoDTOResponse findById(Long id) {
        Produto produto = repository.findById(id);

        if (produto == null)
            return null;
        return ProdutoDTOResponse.valueOf(produto);
    }

    @Override
    public ProdutoDTOResponse create(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setTextura(Textura.valueOf(dto.idTextura()));
        repository.persist(produto); // manter os dados no BD
        return ProdutoDTOResponse.valueOf(produto);
    }

    @Override
    public void update(Long id, ProdutoDTO dto) {
        Produto produto = repository.findById(id);
        produto.setTextura(Textura.valueOf(dto.idTextura()));
        for (VarianteProduto variante : produto.getVarianteProdutos()) {
            variante.setProduto(produto);
            repository.persist(produto);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
