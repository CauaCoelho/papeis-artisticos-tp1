package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
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
        return repository.findByTextura(textura); 
    }

    @Override
    public BlocoDTOResponse findById(Long id) {
        Bloco bloco = repository.findById(id);
       return BlocoDTOResponse.valueOf(bloco);
    }

    @Override
    public BlocoDTOResponse create(BlocoDTO dto) {
        Bloco bloco = new Bloco();
        repository.persist(bloco); //manter os dados no BD
        return BlocoDTOResponse.valueOf(bloco);
    }

    @Override
    public void update(Long id, BlocoDTO dto) {
        Bloco bloco = repository.findById(id);
        bloco.setFormato(null);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    
}
