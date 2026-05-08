package br.unitins.tp1.service;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraDTOResponse;
import br.unitins.tp1.dto.ItemPedidoDTO;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Compra;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.VarianteProduto;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.CompraRepository;
import br.unitins.tp1.repository.VarianteProdutoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {
    @Inject
    CompraRepository repository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    VarianteProdutoRepository varianteRepository;

    @Override
    public void create(CompraDTO dto) {
        Compra compra = new Compra();
        Cliente cliente = clienteRepository.findById(dto.clienteId());

        compra.setCliente(cliente);
        compra.setFormaDePagamento(dto.formaDePagamento());
        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDTO.quantidade());
            VarianteProduto variante = varianteRepository.findById(itemDTO.varianteProdutoId());
            item.setVariante(variante);
            item.setCompra(compra);
            itens.add(item);
        }
        compra.setItens(itens);
        repository.persist(compra);
    }

    @Override
    public PageResponse<CompraDTOResponse> findAll(int page, int pageSize) {
        // 1. Cria a query básica
        PanacheQuery<Compra> query = repository.findAll();

        // 2. Aplica a paginação e converte para DTO usando o método valueOf
        List<CompraDTOResponse> list = query.page(page, pageSize)
                .stream()
                .map(CompraDTOResponse::valueOf)
                .toList();

        // 3. Obtém o total de registros para o [length] do mat-paginator
        long total = query.count();

        return new PageResponse<>(list, total);
    }
}