package br.unitins.tp1.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unitins.tp1.dto.UsuarioDTOResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Override
    public String generateJwt(UsuarioDTOResponse dto) {
        throw new RuntimeException("TESTE");
        /*
         * Instant now = Instant.now();
         * Instant expiryDate = now.plus(EXPIRATION_TIME);
         * 
         * Set<String> roles = new HashSet<String>();
         * roles.add(dto.perfil().getNome());
         * 
         * return Jwt.issuer(issuer)
         * .subject(dto.username())
         * .upn(dto.username())
         * .groups(roles)
         * .expiresAt(expiryDate)
         * .sign();
         */

    }

}