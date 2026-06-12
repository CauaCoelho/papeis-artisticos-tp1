package br.unitins.tp1.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.dto.CompraDTOResponse;
import br.unitins.tp1.service.CompraService;
import br.unitins.tp1.service.UsuarioLogadoService;
import br.unitins.tp1.service.UsuarioService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Resource para gerenciar compras do e-commerce.
 * 
 * Endpoints:
 * - POST /compras : Realizar uma nova compra
 * - GET /compras : Listar todas as compras (admin)
 * - GET /compras/me : Listar compras do usuário autenticado
 * - GET /compras/{id} : Buscar detalhes de uma compra
 */
@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioLogadoService usuarioLogadoService;

    /**
     * Realiza uma nova compra.
     * Calcula o total, valida o cupom (se fornecido) e persiste a compra.
     * 
     * @param dto DTO com dados da compra
     * @return Resposta com a compra criada
     */
    @POST
    @RolesAllowed("USER")
    @Transactional
    public Response realizarCompra(CompraDTO dto) {
        Long usuarioId = usuarioLogadoService.getIdUsuarioLogado();
        CompraDTOResponse compra = compraService.create(usuarioId, dto);
        return Response.status(Status.CREATED).entity(compra).build();
    }

    /**
     * Lista todas as compras (somente admin).
     * 
     * @param page     Número da página (0-based)
     * @param pageSize Tamanho da página
     * @return Página com compras
     */
    @GET
    @RolesAllowed("ADMIN")
    public Response consultarCompras(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return Response.ok(compraService.findAll(page, pageSize)).build();
    }

    /**
     * Lista as compras do usuário autenticado.
     * 
     * @param page     Número da página (0-based)
     * @param pageSize Tamanho da página
     * @return Página com compras do usuário
     */
    @GET
    @Path("/me")
    @RolesAllowed("USER")
    public Response minhasCompras(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        Long usuarioId = usuarioLogadoService.getIdUsuarioLogado();

        return Response.ok(
                compraService.findByUsuario(
                        usuarioId,
                        page,
                        pageSize))
                .build();
    }

    /**
     * Busca detalhes de uma compra específica.
     * O usuário deve ser o proprietário da compra ou ser admin.
     * 
     * @param id ID da compra
     * @return Detalhes da compra
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({ "USER", "ADMIN" })
    public Response buscarCompra(@PathParam("id") Long id) {
        CompraDTOResponse compra = compraService.findById(id);

        if (compra == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(compra).build();
    }
}
