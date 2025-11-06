package br.unitins.tp1.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.BlocoDTO;
import br.unitins.tp1.dto.BlocoDTOResponse;
import br.unitins.tp1.service.BlocoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class BlocoResourceTest {
    @Inject
    BlocoService blocoService;

    @Test
    void buscarTodosTest(){
        RestAssured.given()
        .when()
            .get("/blocos")
        .then()
            .statusCode(200);
    }

    @Test
    void incluirTest(){
        BlocoDTO dto = new BlocoDTO(dto.textura(), dto.formato());

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
            .post()
            .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
            "", CoreMatchers.is(null));
    }

    @Test
    void alterarTest(){
        BlocoDTO dto = new BlocoDTO(dto.quantidadeFolhas());

        BlocoDTOResponse response = BlocoService.create(dto);

        BlocoDTO dtoUpdate = new BlocoDTO(1);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .when()
            .put("/blocos/" + response.id())
        .then()
            .statusCode(204);   

        response = blocoService.findById(response.id());
        assertEquals(dtoUpdate.quantidadeFolhas(), response.quantidadeFolhas());
    }
}
