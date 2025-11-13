package br.unitins.tp1.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.SketchbookDTO;
import br.unitins.tp1.dto.SketchbookDTOResponse;
import br.unitins.tp1.model.Capa;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.SketchbookService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class SketchbookResourceTest {
    @Inject
    SketchbookService sketchbookService;

    @Test
    void buscarTodosTest(){
        RestAssured.given()
        .when()
            .get("/sketchbooks")
        .then()
            .statusCode(200);
    }

    @Test
    void incluirTest(){
        SketchbookDTO dto = new SketchbookDTO(new Capa(), 98, Textura.CASCA_DE_OVO, Formato.B5);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
            .post()
            .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
            "capa", CoreMatchers.is(dto.capa()),
            "textura", CoreMatchers.is(dto.textura()),
            "formato", CoreMatchers.is(dto.formato()));
    }

    @Test
    void alterarTest(){
        SketchbookDTO dto = new SketchbookDTO(new Capa(), 98, Textura.TRANCADO, Formato.B3);

        SketchbookDTOResponse response = sketchbookService.create(dto);

        SketchbookDTO dtoUpdate = new SketchbookDTO(dto.capa(), 1, dto.textura(), dto.formato());

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .when()
            .put("/sketchbooks/" + response.id())
        .then()
            .statusCode(204);   

        response = sketchbookService.findById(response.id());
        assertEquals(dtoUpdate.formato(), response.formato());
        assertEquals(dtoUpdate.textura(), response.textura());
    }
}
