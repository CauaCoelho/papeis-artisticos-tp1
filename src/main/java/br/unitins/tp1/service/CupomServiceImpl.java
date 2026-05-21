package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.CupomDTO;
import br.unitins.tp1.dto.CupomDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Cupom;
import br.unitins.tp1.repository.CupomRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CupomServiceImpl implements CupomService {

    @Inject
    CupomRepository repository;

    @Override
    public PageResponse<CupomDTOResponse> findAll(int page, int pageSize) {
        PanacheQuery<Cupom> query = repository.findAll();

        List<CupomDTOResponse> list = query.page(page, pageSize)
                .stream()
                .map(CupomDTOResponse::valueOf)
                .toList();

        long total = query.count();

        return new PageResponse<>(list, total);
    }

    @Override
    public Cupom findById(Long id) {
        Cupom cupom = repository.findById(id);

        if (cupom == null)
            return null;
        return cupom;
    }

    @Override
    public List<Cupom> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo).list();
    }

    @Override
    public Cupom create(CupomDTO dto) {
        Cupom cupom = new Cupom();
        cupom.setCodigo(dto.codigo());
        cupom.setValor(dto.valor());
        cupom.setValidade(dto.validade());
        
        repository.persist(cupom);
        return cupom;
    }

    @Override
    public void update(Long id, CupomDTO dto) {
        Cupom cupom = repository.findById(id);
        cupom.setCodigo(dto.codigo());
        cupom.setValor(dto.valor());
        cupom.setValidade(dto.validade());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.findAll().count();
    }

    @Override
    public long count(String codigo) {
        return repository.findByCodigo(codigo).count();
    }
}
