package br.unitins.tp1.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.BlocoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@Transactional
public class BlocoResourceTest {
    @Inject
    BlocoService blocoService;

    @Test
    void buscarTodosTest(){
        RestAssured.given()
        .when()
            .get("papeis/blocos")
        .then()
            .statusCode(200);
    }

    @Test
    void incluirTest(){
        BlocoDTO dto = new BlocoDTO(200, Textura.LISO );

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
            .post()
            .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
            "textura", CoreMatchers.is(Textura.LISO));
    }

    @Test
    void alterarTest(){
        BlocoDTO dto = new BlocoDTO(100, Textura.KRAFT);

        BlocoDTOResponse response = blocoService.create(dto);

        BlocoDTO dtoUpdate = new BlocoDTO(300, Textura.TRANCADO);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .when()
            .put("papeis/blocos" + response.id())
        .then()
            .statusCode(204);   

        response = blocoService.findById(response.id());
        assertEquals(dtoUpdate.quantidadeFolhas(), response.quantidadeFolhas());
    }
}
