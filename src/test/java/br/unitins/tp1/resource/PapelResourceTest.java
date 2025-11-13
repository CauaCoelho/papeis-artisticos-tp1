package br.unitins.tp1.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.PapelDTO;
import br.unitins.tp1.dto.PapelDTOResponse;
import br.unitins.tp1.model.Formato;
import br.unitins.tp1.model.Textura;
import br.unitins.tp1.service.PapelService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class PapelResourceTest {
    @Inject
    PapelService papelService;

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
        Formato formato = Formato.values()[new Random().nextInt(Formato.values().length)];
        PapelDTO dto = new PapelDTO(textura, formato);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dto)
        .when()
            .post()
            .then()
            .statusCode(201)
            .body("id", CoreMatchers.notNullValue(),
            "textura", CoreMatchers.is(dto.textura()),
            "formato", CoreMatchers.is(dto.formato()));
    }

    @Test
    void alterarTest(){
        PapelDTO dto = new PapelDTO(Textura.LISO, Formato.A3);

        PapelDTOResponse response = papelService.create(dto);

        PapelDTO dtoUpdate = new PapelDTO(Textura.CASCA_DE_OVO, Formato.A4);

        RestAssured.given()
        .contentType(ContentType.JSON)
        .body(dtoUpdate)
        .when()
            .put("/papeis/" + response.id())
        .then()
            .statusCode(204);   

        response = papelService.findById(response.id());
        assertEquals(dtoUpdate.formato(), response.formato());
        assertEquals(dtoUpdate.textura(), response.textura());
    }
}
