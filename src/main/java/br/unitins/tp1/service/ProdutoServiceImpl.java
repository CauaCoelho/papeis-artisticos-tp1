package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.dto.ProdutoDTOResponse;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService{

    @Inject
    ProdutoRepository repository;

    @Override
    public List<Produto> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Produto> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
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
        repository.persist(produto); //manter os dados no BD
        return ProdutoDTOResponse.valueOf(produto);
    }

    @Override
    public void update(Long id, ProdutoDTO dto) {
        Produto produto = repository.findById(id);
         produto.setTextura(Textura.valueOf(dto.idTextura()));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
}
