package br.unitins.tp1.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.unitins.tp1.dto.AuthDTOResponse;
import br.unitins.tp1.dto.AuthUsuarioDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class KeycloakAuthServiceImpl implements KeycloakAuthService {

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "keycloak.server-url")
    String keycloakServerUrl;

    @ConfigProperty(name = "keycloak.realm")
    String realm;

    @ConfigProperty(name = "keycloak.client-id")
    String clientId;

    @ConfigProperty(name = "keycloak.client-secret")
    String clientSecret;

    @ConfigProperty(name = "keycloak.request-timeout-ms", defaultValue = "10000")
    int requestTimeoutMs;

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @Override
    public AuthDTOResponse login(AuthUsuarioDTO authDTO) {
        HttpRequest request = HttpRequest.newBuilder(tokenEndpoint())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .POST(HttpRequest.BodyPublishers.ofString(buildFormData(authDTO)))
                .build();

        HttpResponse<String> response = send(request);

        if (response.statusCode() == 400 || response.statusCode() == 401) {
            throw new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED);
        }

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao autenticar no Keycloak", Status.BAD_GATEWAY);
        }

        return parseAuthResponse(response.body());
    }

    private URI tokenEndpoint() {
        return URI.create(String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, realm));
    }

    private String buildFormData(AuthUsuarioDTO authDTO) {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("grant_type", "password");
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);
        formData.put("username", authDTO.login());
        formData.put("password", authDTO.senha());

        StringJoiner joiner = new StringJoiner("&");
        formData.forEach((key, value) -> joiner.add(urlEncode(key) + "=" + urlEncode(value)));
        return joiner.toString();
    }

    private HttpResponse<String> send(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao comunicar com o Keycloak", e, Status.BAD_GATEWAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebApplicationException("Autenticacao interrompida", e, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private AuthDTOResponse parseAuthResponse(String responseBody) {
        try {
            JsonNode payload = objectMapper.readTree(responseBody);
            JsonNode accessToken = payload.get("access_token");

            if (accessToken == null || accessToken.asText().isBlank()) {
                throw new WebApplicationException("Resposta invalida do Keycloak", Status.BAD_GATEWAY);
            }

            String tokenType = payload.path("token_type").asText("Bearer");
            return new AuthDTOResponse(accessToken.asText(), tokenType);
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao processar resposta do Keycloak", e, Status.BAD_GATEWAY);
        }
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}