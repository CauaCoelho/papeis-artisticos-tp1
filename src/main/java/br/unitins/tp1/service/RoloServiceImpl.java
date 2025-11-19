package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.RoloDTO;
import br.unitins.tp1.dto.RoloDTOResponse;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.RoloRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RoloServiceImpl implements RoloService{

    @Inject
    RoloRepository repository;

    @Override
    public List<Rolo> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Rolo> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
    }

    @Override
    public RoloDTOResponse findById(Long id) {
       Rolo rolo = repository.findById(id);
       
       if (rolo == null) 
        return null;
       return RoloDTOResponse.valueOf(rolo);
    }

    @Override
    public RoloDTOResponse create(RoloDTO dto) {
        Rolo rolo = new Rolo();
        rolo.setTextura(dto.textura());
        rolo.setFormato(dto.formato());
        repository.persist(rolo); //manter os dados no BD
        return RoloDTOResponse.valueOf(rolo);
    }

    @Override
    public void update(Long id, RoloDTO dto) {
        Rolo rolo = repository.findById(id);
        rolo.setTextura(dto.textura());
        rolo.setFormato(dto.formato());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Rolo> findByFormato(Formato formato) {
        return repository.findByFormato(formato);
    }
    
}
