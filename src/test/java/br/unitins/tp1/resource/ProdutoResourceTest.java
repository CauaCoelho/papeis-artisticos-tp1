package br.unitins.tp1.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.ProdutoDTO;
import br.unitins.tp1.dto.ProdutoDTOResponse;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.ProdutoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@Transactional
public class ProdutoResourceTest {
    @Inject
    ProdutoService produtoService;

    @Test
    void buscarTodosTest(){
        RestAssured.given()
        .when()
            .get("/papeis")
        .then()
            .statusCode(200);
    }

    @Test
    void incluirTest(){
        Textura textura = Textura.values()[new Random().nextInt(Textura.values().length)];
        ProdutoDTO dto = new ProdutoDTO(textura);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
            .post()
            .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
            "textura", CoreMatchers.is(dto.textura()));
    }

    @Test
    void alterarTest(){
        ProdutoDTO dto = new ProdutoDTO(Textura.LISO);

        ProdutoDTOResponse response = produtoService.create(dto);

        ProdutoDTO dtoUpdate = new ProdutoDTO(Textura.CASCA_DE_OVO);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .when()
            .put("/papeis/" + response.id())
        .then()
            .statusCode(204);   

        response = produtoService.findById(response.id());
        assertEquals(dtoUpdate.textura(), response.textura());
    }
}
