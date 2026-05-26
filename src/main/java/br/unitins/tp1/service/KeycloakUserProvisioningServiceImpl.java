package br.unitins.tp1.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.unitins.tp1.dto.UsuarioDTO;
import br.unitins.tp1.exception.ValidationException;
import br.unitins.tp1.model.Perfil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class KeycloakUserProvisioningServiceImpl implements KeycloakUserProvisioningService {

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "keycloak.server-url")
    String keycloakServerUrl;

    @ConfigProperty(name = "keycloak.realm")
    String realm;

    @ConfigProperty(name = "keycloak.admin.realm")
    String adminRealm;

    @ConfigProperty(name = "keycloak.admin.client-id")
    String adminClientId;

    @ConfigProperty(name = "keycloak.admin.username")
    String adminUsername;

    @ConfigProperty(name = "keycloak.admin.password")
    String adminPassword;

    @ConfigProperty(name = "keycloak.request-timeout-ms", defaultValue = "10000")
    int requestTimeoutMs;

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @Override
    public void createUser(UsuarioDTO dto) {
        String accessToken = getAdminAccessToken();
        HttpResponse<String> createResponse = send(HttpRequest.newBuilder(usersEndpoint())
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .POST(HttpRequest.BodyPublishers.ofString(buildCreateUserPayload(dto)))
                .build());

        if (createResponse.statusCode() == 409) {
            throw ValidationException.of("username", "O username informado já existe no Keycloak.");
        }

        if (createResponse.statusCode() < 200 || createResponse.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao criar usuario no Keycloak", Status.BAD_GATEWAY);
        }

        String userId = findUserIdByUsername(accessToken, dto.login());
        try {
            assignRealmRoles(accessToken, userId, resolveRoleNames(dto.idPerfil()));
        } catch (RuntimeException e) {
            deleteUser(dto.login());
            throw e;
        }
    }

    @Override
    public void deleteUser(String username) {
        String accessToken = getAdminAccessToken();
        String userId = findUserIdByUsername(accessToken, username);
        if (userId == null || userId.isBlank()) {
            return;
        }

        HttpResponse<String> response = send(HttpRequest.newBuilder(userByIdEndpoint(userId))
                .header("Authorization", "Bearer " + accessToken)
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .DELETE()
                .build());

        if (response.statusCode() != 204 && response.statusCode() != 404) {
            throw new WebApplicationException("Falha ao remover usuario do Keycloak", Status.BAD_GATEWAY);
        }
    }

    private String getAdminAccessToken() {
        HttpRequest request = HttpRequest.newBuilder(adminTokenEndpoint())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .POST(HttpRequest.BodyPublishers.ofString(buildAdminTokenFormData()))
                .build();

        HttpResponse<String> response = send(request);
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao autenticar administracao no Keycloak", Status.BAD_GATEWAY);
        }

        try {
            JsonNode payload = objectMapper.readTree(response.body());
            JsonNode accessToken = payload.get("access_token");
            if (accessToken == null || accessToken.asText().isBlank()) {
                throw new WebApplicationException("Resposta invalida do Keycloak", Status.BAD_GATEWAY);
            }
            return accessToken.asText();
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao processar token administrativo do Keycloak", e,
                    Status.BAD_GATEWAY);
        }
    }

    private void assignRealmRoles(String accessToken, String userId, List<String> roleNames) {
        ArrayNode rolesPayload = objectMapper.createArrayNode();
        for (String roleName : roleNames) {
            HttpResponse<String> roleResponse = send(HttpRequest.newBuilder(roleEndpoint(roleName))
                    .header("Authorization", "Bearer " + accessToken)
                    .timeout(Duration.ofMillis(requestTimeoutMs))
                    .GET()
                    .build());

            if (roleResponse.statusCode() < 200 || roleResponse.statusCode() >= 300) {
                throw new WebApplicationException("Falha ao buscar perfil no Keycloak", Status.BAD_GATEWAY);
            }

            try {
                rolesPayload.add(objectMapper.readTree(roleResponse.body()));
            } catch (IOException e) {
                throw new WebApplicationException("Erro ao processar perfil do Keycloak", e, Status.BAD_GATEWAY);
            }
        }

        HttpResponse<String> assignResponse = send(HttpRequest.newBuilder(userRealmRolesEndpoint(userId))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .POST(HttpRequest.BodyPublishers.ofString(rolesPayload.toString()))
                .build());

        if (assignResponse.statusCode() < 200 || assignResponse.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao associar perfil ao usuario no Keycloak", Status.BAD_GATEWAY);
        }
    }

    private String findUserIdByUsername(String accessToken, String username) {
        URI endpoint = URI.create(usersEndpoint() + "?username=" + urlEncode(username) + "&exact=true");
        HttpResponse<String> response = send(HttpRequest.newBuilder(endpoint)
                .header("Authorization", "Bearer " + accessToken)
                .timeout(Duration.ofMillis(requestTimeoutMs))
                .GET()
                .build());

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new WebApplicationException("Falha ao localizar usuario no Keycloak", Status.BAD_GATEWAY);
        }

        try {
            JsonNode payload = objectMapper.readTree(response.body());
            if (!payload.isArray() || payload.isEmpty()) {
                return null;
            }

            JsonNode user = payload.get(0);
            JsonNode id = user.get("id");
            return id == null ? null : id.asText();
        } catch (IOException e) {
            throw new WebApplicationException("Erro ao processar usuario do Keycloak", e, Status.BAD_GATEWAY);
        }
    }

    private String buildCreateUserPayload(UsuarioDTO dto) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("username", dto.nome());
        payload.put("firstName", resolveFirstName(dto.nome(), dto.username()));
        payload.put("lastName", resolveLastName(dto.nome()));
        payload.put("email", dto.login() + "@tp2.local");
        payload.put("enabled", true);
        payload.put("emailVerified", true);

        ObjectNode credential = objectMapper.createObjectNode();
        credential.put("type", "password");
        credential.put("value", dto.senha());
        credential.put("temporary", false);

        ArrayNode credentials = objectMapper.createArrayNode();
        credentials.add(credential);
        payload.set("credentials", credentials);

        return payload.toString();
    }

    private List<String> resolveRoleNames(int idPerfil) {
        Perfil perfil = Perfil.valueOf(idPerfil);
        List<String> roles = new ArrayList<>();
        if (perfil == Perfil.ADM) {
            roles.add("Adm");
        }
        roles.add("User");
        return roles;
    }

    private String resolveFirstName(String nome, String username) {
        if (nome == null || nome.isBlank()) {
            return username;
        }

        String[] partes = nome.trim().split("\\s+", 2);
        return partes[0];
    }

    private String resolveLastName(String nome) {
        if (nome == null || nome.isBlank()) {
            return "Usuario";
        }

        String[] partes = nome.trim().split("\\s+", 2);
        return partes.length > 1 ? partes[1] : "Usuario";
    }

    private URI adminTokenEndpoint() {
        return URI.create(String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, adminRealm));
    }

    private URI usersEndpoint() {
        return URI.create(String.format("%s/admin/realms/%s/users", keycloakServerUrl, realm));
    }

    private URI userByIdEndpoint(String userId) {
        return URI.create(String.format("%s/admin/realms/%s/users/%s", keycloakServerUrl, realm, userId));
    }

    private URI roleEndpoint(String roleName) {
        return URI.create(String.format("%s/admin/realms/%s/roles/%s", keycloakServerUrl, realm, urlEncode(roleName)));
    }

    private URI userRealmRolesEndpoint(String userId) {
        return URI.create(String.format("%s/admin/realms/%s/users/%s/role-mappings/realm", keycloakServerUrl, realm,
                userId));
    }

    private String buildAdminTokenFormData() {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("grant_type", "password");
        formData.put("client_id", adminClientId);
        formData.put("username", adminUsername);
        formData.put("password", adminPassword);

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
            throw new WebApplicationException("Operacao com Keycloak interrompida", e, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}