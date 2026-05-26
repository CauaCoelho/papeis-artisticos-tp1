package br.unitins.tp1.service;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraDTOResponse;
import br.unitins.tp1.dto.PageResponse;
import br.unitins.tp1.model.Compra;

public interface CompraService {
    
    /**
     * Cria uma nova compra.
     * Calcula o total, valida o cupom (se fornecido) e aplica desconto.
     * 
     * @param usuarioId ID do usuário autenticado
     * @param dto DTO da compra com itens e dados de pagamento
     * @return A compra criada
     */
    CompraDTOResponse create(Long usuarioId, CompraDTO dto);

    PageResponse<CompraDTOResponse> findAll(int page, int pageSize);
    
    /**
     * Busca as compras de um usuário específico.
     * 
     * @param usuarioId ID do usuário
     * @param page Número da página (0-based)
     * @param pageSize Tamanho da página
     * @return Página com compras do usuário
     */
    PageResponse<CompraDTOResponse> findByUsuario(Long usuarioId, int page, int pageSize);
    
    /**
     * Busca uma compra específica pelo ID.
     * 
     * @param id ID da compra
     * @return DTO da compra ou null se não encontrada
     */
    CompraDTOResponse findById(Long id);
    
    /**
     * Calcula o total da compra com base nos itens e cupom.
     * 
     * @param itens Lista de itens da compra
     * @param cupomId ID do cupom (pode ser null)
     * @return Total da compra após aplicar desconto do cupom (se houver)
     */
    BigDecimal calcularTotal(List<Long> varianteProdutoIds, List<Integer> quantidades, Long cupomId);
}
