package br.unitins.tp1.resource;

import br.unitins.tp1.model.Capa;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/capas")
@Produces(MediaType.APPLICATION_JSON)
public class CapaResource {

    @GET
    public Response getCapas() {
        return Response.ok(Capa.values()).build();
    }
}
