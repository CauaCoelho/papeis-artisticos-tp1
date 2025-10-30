package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDto;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.BlocoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BlocoServiceImpl implements BlocoService{

    @Inject
    BlocoRepository repository;

    @Override
    public List<Bloco> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Bloco> findByTextura(Textura textura) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTextura'");
    }

    @Override
    public Bloco findById(Long id) {
       return repository.findById(id);
    }

    @Override
    public Bloco create(BlocoDto dto) {
        Bloco bloco = new Bloco();
        repository.persist(bloco); //manter os dados no BD
        return bloco;
    }

    @Override
    public void update(Long id, BlocoDto dto) {
        Bloco bloco = repository.findById(id);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    
}
