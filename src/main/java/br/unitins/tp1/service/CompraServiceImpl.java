package br.unitins.tp1.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraDTOResponse;
import br.unitins.tp1.dto.ItemPedidoDTO;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Compra;
import br.unitins.tp1.model.Cupom;
import br.unitins.tp1.model.ItemPedido;
import br.unitins.tp1.model.VarianteProduto;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.CompraRepository;
import br.unitins.tp1.repository.CupomRepository;
import br.unitins.tp1.repository.VarianteProdutoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {
    @Inject
    CompraRepository repository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    VarianteProdutoRepository varianteRepository;
    @Inject
    CupomRepository cupomRepository;

    @Override
    public CompraDTOResponse create(Long usuarioId, CompraDTO dto) {
        // Valida cliente
        Cliente cliente = clienteRepository.findById(usuarioId);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado", Status.NOT_FOUND);
        }

        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setFormaDePagamento(dto.formaDePagamento());
        compra.setDataPedido(LocalDateTime.now());

        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDTO : dto.itens()) {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDTO.quantidade());
            
            VarianteProduto variante = varianteRepository.findById(itemDTO.varianteProdutoId());
            if (variante == null) {
                throw new WebApplicationException("Variante de produto não encontrada", Status.NOT_FOUND);
            }
            
            item.setVariante(variante);
            item.setPreco(variante.getPreco()); // Captura o preço no momento da compra
            item.setCompra(compra);
            itens.add(item);
            
            // Calcula o subtotal do item
            BigDecimal subtotal = variante.getPreco().multiply(new BigDecimal(itemDTO.quantidade()));
            total = total.add(subtotal);
        }

        compra.setItens(itens);

        // Aplica cupom se fornecido
        if (dto.cupomId() != null) {
            Cupom cupom = cupomRepository.findById(dto.cupomId());
            if (cupom == null) {
                throw new WebApplicationException("Cupom não encontrado", Status.NOT_FOUND);
            }
            
            if (cupom.getValidade().isBefore(LocalDateTime.now())) {
                throw new WebApplicationException("Cupom expirado", Status.BAD_REQUEST);
            }
            
            // Verifica se o cupom é válido para todos os produtos da compra
            boolean todosPermitidos = itens.stream()
                    .allMatch(item -> isCupomValidoParaProduto(cupom, item.getVariante()));
            if (!todosPermitidos) {
                throw new WebApplicationException("Cupom não é válido para todos os produtos da compra", Status.BAD_REQUEST);
            }

            compra.setCupomAplicado(cupom);
            total = total.subtract(cupom.getValor());
            
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }
        }

        compra.setTotal(total);
        repository.persist(compra);

        return CompraDTOResponse.valueOf(compra);
    }

    private boolean isCupomValidoParaProduto(Cupom cupom, VarianteProduto variante) {
        if (cupom.getProdutosPermitidos() == null || cupom.getProdutosPermitidos().isEmpty()) {
            return true;
        }
        return cupom.getProdutosPermitidos().stream()
                .anyMatch(produto -> produto.getId().equals(variante.getProduto().getId()));
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

    @Override
    public PageResponse<CompraDTOResponse> findByUsuario(Long usuarioId, int page, int pageSize) {
        PanacheQuery<Compra> query = repository.find("cliente.id = ?1", usuarioId);

        List<CompraDTOResponse> list = query.page(page, pageSize)
                .stream()
                .map(CompraDTOResponse::valueOf)
                .toList();

        long total = query.count();

        return new PageResponse<>(list, total);
    }

    @Override
    public CompraDTOResponse findById(Long id) {
        Compra compra = repository.findById(id);
        if (compra == null) {
            return null;
        }
        return CompraDTOResponse.valueOf(compra);
    }

    @Override
    public BigDecimal calcularTotal(List<Long> varianteProdutoIds, List<Integer> quantidades, Long cupomId) {
        if (varianteProdutoIds.size() != quantidades.size()) {
            throw new IllegalArgumentException("Quantidade de variantes e quantidades não correspondem");
        }

        BigDecimal total = BigDecimal.ZERO;

        // Calcula total dos itens
        for (int i = 0; i < varianteProdutoIds.size(); i++) {
            VarianteProduto variante = varianteRepository.findById(varianteProdutoIds.get(i));
            if (variante == null) {
                throw new WebApplicationException("Variante não encontrada", Status.NOT_FOUND);
            }
            
            BigDecimal subtotal = variante.getPreco().multiply(new BigDecimal(quantidades.get(i)));
            total = total.add(subtotal);
        }

        // Aplica cupom se fornecido
        if (cupomId != null) {
            Cupom cupom = cupomRepository.findById(cupomId);
            if (cupom != null && cupom.getValidade().isAfter(LocalDateTime.now())) {
                total = total.subtract(cupom.getValor());
                if (total.compareTo(BigDecimal.ZERO) < 0) {
                    total = BigDecimal.ZERO;
                }
            }
        }

        return total;
    }
}