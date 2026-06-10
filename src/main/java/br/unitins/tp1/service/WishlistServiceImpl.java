package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.WishlistDTO;
import br.unitins.tp1.dto.WishlistDTOResponse;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Produto;
import br.unitins.tp1.model.Wishlist;
import br.unitins.tp1.repository.ClienteRepository;
import br.unitins.tp1.repository.ProdutoRepository;
import br.unitins.tp1.repository.WishlistRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class WishlistServiceImpl implements WishlistService {

    @Inject
    WishlistRepository repository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Override
    public WishlistDTOResponse adicionar(Long usuarioId, WishlistDTO dto) {
        // Verifica se o produto já está na wishlist
        if (repository.existsByUsuarioAndProduto(usuarioId, dto.produtoId())) {
            throw new WebApplicationException(
                    "Produto já existe na wishlist",
                    Status.CONFLICT);
        }

        // Busca o usuário
        Cliente usuario = clienteRepository.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException(
                    "Usuário não encontrado",
                    Status.NOT_FOUND);
        }

        // Busca o produto
        Produto produto = produtoRepository.findById(dto.produtoId());
        if (produto == null) {
            throw new WebApplicationException(
                    "Produto não encontrado",
                    Status.NOT_FOUND);
        }

        // Cria a wishlist
        Wishlist wishlist = new Wishlist();
        wishlist.setUsuario(usuario);
        wishlist.setProduto(produto);

        repository.persist(wishlist);

        return WishlistDTOResponse.valueOf(wishlist);
    }

    @Override
    public List<WishlistDTOResponse> listar(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(WishlistDTOResponse::valueOf)
                .toList();
    }

    @Override
    public void remover(Long usuarioId, Long produtoId) {
        long deletados = repository.deleteByUsuarioAndProduto(usuarioId, produtoId);
        if (deletados == 0) {
            throw new WebApplicationException(
                    "Item não encontrado na wishlist",
                    Status.NOT_FOUND);
        }
    }
}
