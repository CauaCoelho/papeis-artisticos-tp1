package br.unitins.tp1.resource;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.FileService;
import br.unitins.tp1.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/papeis")
@Produces(MediaType.APPLICATION_JSON) // Tipo de conteúdo que vai ser produzido
@Consumes(MediaType.APPLICATION_JSON) // Tipo de conteúdo consumido; Por a anotação estar na classe, então vale para
                                      // todos os métodos
public class ProdutoResource {

    @Inject // injeção de dependência
    ProdutoService service;

    @Inject
    FileService fileService;

    @GET
    public Response buscarTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/find/{textura}") // Vai adicionar uma segunda camada de recurso: site.com/papeis/find/{textura}
    public Response buscarPorTextura(Textura textura) {
        return Response.ok(service.findByTextura(textura)).build();
    }

    @POST
    @Transactional
    public Response incluir(@Valid ProdutoDTO dto) {
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response editar(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/image/download/{fid}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("fid") String fid) {
        br.unitins.tp1.service.ArquivoDownload download = fileService.download(fid);
        ResponseBuilder response = Response.ok(download.content());
        response.header("Content-Disposition", "attachment;filename=" + download.filename());
        response.header("Content-Type", download.contentType());
        return response.build();
    }

    @PATCH
    @Path("/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(
            @RestForm("idProduto") @NotNull(message = "idProduto é obrigatório.") @Min(value = 1, message = "idProduto deve ser maior ou igual a 1.") Long idProduto,

            @RestForm("file") @NotNull(message = "Arquivo de imagem é obrigatório.") FileUpload file) {

        try {
            fileService.salvar(idProduto, file);
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }
}
