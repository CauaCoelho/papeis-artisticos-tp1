package br.unitins.tp1.util;

import java.time.Duration;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
@IfBuildProfile("dev")
public class KeycloakDevService {

    private static final Logger LOG = Logger.getLogger(KeycloakDevService.class);

    private static final String IMAGE = "keycloak/keycloak:26.2.5";
    private static final int KEYCLOAK_CONTAINER_PORT = 8080;
    private static final int KEYCLOAK_HOST_PORT = 8180;
    private static final String REALM_IMPORT = "/opt/keycloak/data/import/tp2-realm.json";

    @Inject
    @ConfigProperty(name = "keycloak.devservice.enabled", defaultValue = "true")
    boolean enabled;

    private GenericContainer<?> container;

    void onStart(@Observes StartupEvent ev) {
        if (!enabled) {
            LOG.info("[Keycloak DevService] desabilitado via keycloak.devservice.enabled=false");
            return;
        }

        LOG.info("[Keycloak DevService] iniciando container...");

        @SuppressWarnings("resource")
        GenericContainer<?> c = new GenericContainer<>(IMAGE);
        c.withEnv("KC_BOOTSTRAP_ADMIN_USERNAME", "admin");
        c.withEnv("KC_BOOTSTRAP_ADMIN_PASSWORD", "admin");
        c.withCommand("start-dev", "--http-port=8080", "--import-realm");
        c.withCopyFileToContainer(
                MountableFile.forClasspathResource("keycloak/tp2-realm.json"),
                REALM_IMPORT);
        c.withExposedPorts(KEYCLOAK_CONTAINER_PORT);
        c.withStartupTimeout(Duration.ofSeconds(120));
        c.waitingFor(Wait.forHttp("/realms/tp2/.well-known/openid-configuration")
                .forPort(KEYCLOAK_CONTAINER_PORT)
                .withStartupTimeout(Duration.ofSeconds(120)));

        c.setPortBindings(List.of(KEYCLOAK_HOST_PORT + ":" + KEYCLOAK_CONTAINER_PORT));
        container = c;

        try {
            container.start();
            LOG.infof("[Keycloak DevService] online - auth server: http://localhost:%d", KEYCLOAK_HOST_PORT);
        } catch (Exception e) {
            LOG.warnf(
                    "[Keycloak DevService] falhou ao iniciar o container (rode 'docker pull %s' manualmente se necessario): %s",
                    IMAGE, e.getMessage());
            container = null;
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        if (container != null && container.isRunning()) {
            LOG.info("[Keycloak DevService] parando container...");
            container.stop();
        }
    }
}
