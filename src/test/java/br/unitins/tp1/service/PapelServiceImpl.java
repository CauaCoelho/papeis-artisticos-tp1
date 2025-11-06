package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelDTO;
import br.unitins.tp1.model.Papel;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.PapelRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PapelServiceImpl implements PapelService{

    @Inject
    PapelRepository repository;

    @Override
    public List<Papel> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Papel> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
    }

    @Override
    public Papel findById(Long id) {
       return repository.findById(id);
    }

    @Override
    public Papel create(PapelDTO dto) {
        Papel papel = new Papel();
        papel.setTextura(dto.textura());
        papel.setFormato(dto.formato());
        repository.persist(papel); //manter os dados no BD
        return papel;
    }

    @Override
    public void update(Long id, PapelDTO dto) {
        Papel papel = repository.findById(id);
        papel.setTextura(dto.textura());
        papel.setFormato(dto.formato());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
