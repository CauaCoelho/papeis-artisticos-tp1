package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PapelAvulsoDTO;
import br.unitins.tp1.dto.PapelAvulsoDTOResponse;
import br.unitins.tp1.model.PapelAvulso;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.PapelAvulsoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PapelAvulsoServiceImpl implements PapelAvulsoService{

    @Inject
    PapelAvulsoRepository repository;

    @Override
    public List<PapelAvulso> findAll() {
        return repository.listAll();
    }

    @Override
    public List<PapelAvulso> findByTextura(Textura textura) {
        return repository.findByTextura(textura); 
    }

    @Override
    public PapelAvulsoDTOResponse findById(Long id) {
        PapelAvulso papelavulso = repository.findById(id);
       return PapelAvulsoDTOResponse.valueOf(papelavulso);
    }

    @Override
    public PapelAvulsoDTOResponse create(PapelAvulsoDTO dto) {
        PapelAvulso papelavulso = new PapelAvulso();
        repository.persist(papelavulso); //manter os dados no BD
        return PapelAvulsoDTOResponse.valueOf(papelavulso);
    }

    @Override
    public void update(Long id, PapelAvulsoDTO dto) {
        PapelAvulso papelavulso = repository.findById(id);

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
    
}
