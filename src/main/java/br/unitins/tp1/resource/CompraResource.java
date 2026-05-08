package br.unitins.tp1.resource;

import br.unitins.tp1.dto.CompraDTO;
import br.unitins.tp1.service.CompraServiceImpl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class CompraResource {

    @Inject
    CompraServiceImpl compraService;

    @POST
    @Transactional
    public Response realizarCompra(CompraDTO dto) {
        compraService.create(dto);
        return Response.ok().build();
    }

    @GET
    public Response consultarCompras(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return Response.ok(compraService.findAll(page, pageSize)).build();
    }
}
