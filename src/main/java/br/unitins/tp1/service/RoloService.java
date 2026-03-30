package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.RoloDTO;
import br.unitins.tp1.dto.RoloDTOResponse;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Rolo;
import br.unitins.tp1.model.Textura;

public interface RoloService {
    List<Rolo> findAll();
    List<Rolo> findByTextura(Textura textura);
    List<Rolo> findByComprimento (double comprimento);
    List<Rolo> findByCategoria (Categoria categoria);
    RoloDTOResponse findById(Long id);
    RoloDTOResponse create (RoloDTO dto);
    void update (Long id, RoloDTO dto);
    void delete (Long id);
}
