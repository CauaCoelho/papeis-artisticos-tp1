package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.model.Bloco;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.repository.BlocoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BlocoServiceImpl implements BlocoService {

    @Inject
    BlocoRepository repository;

    @Override
    public List<Bloco> findAll() {
        return repository.listAll();
    }
    @Override
    public Response findByNome(String nome) {
        List<BlocoDTOResponse> blocos = repository.findByNome(nome).stream().map(BlocoDTOResponse::valueOf).toList();
        if (blocos.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(blocos).build();
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
    public List<Bloco> findByQuantidadeFolhas(int quantidadeFolhas) {
        return repository.findByQuantidadeFolhas(quantidadeFolhas);
    }

    @Override
    public List<Bloco> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }

    @Override
    public BlocoDTOResponse create(BlocoDTO dto) {
        Bloco bloco = new Bloco();
        bloco.setQuantidadeFolhas(dto.quantidadeFolhas());
        bloco.setTextura(dto.textura());
        repository.persist(bloco); // manter os dados no BD
        return BlocoDTOResponse.valueOf(bloco);
    }

    @Override
    public void update(Long id, BlocoDTO dto) {
        Bloco bloco = repository.findById(id);
        bloco.setQuantidadeFolhas(dto.quantidadeFolhas());
        bloco.setTextura(dto.textura());

    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

}
