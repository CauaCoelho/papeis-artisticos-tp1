package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.model.Sketchbook;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.SketchbookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SketchbookServiceImpl implements SketchbookService{

    @Inject
    SketchbookRepository repository;

    @Override
    public List<Sketchbook> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Sketchbook> findByTextura(Textura textura) {
        return repository.findByTextura(textura);
    }

    @Override
    public Sketchbook findById(Long id) {
       return repository.findById(id);
    }

    @Override
    public Sketchbook create(SketchbookDTO dto) {
        Sketchbook sketchbook = new Sketchbook();
        sketchbook.setTextura(dto.textura());
        sketchbook.setFormato(dto.formato());
        repository.persist(sketchbook); //manter os dados no BD
        return sketchbook;
    }

    @Override
    public void update(Long id, SketchbookDTO dto) {
        Sketchbook sketchbook = repository.findById(id);
        sketchbook.setTextura(dto.textura());
        sketchbook.setFormato(dto.formato());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
