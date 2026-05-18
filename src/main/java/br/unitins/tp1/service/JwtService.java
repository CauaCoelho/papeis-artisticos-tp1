package br.unitins.tp1.service;

import br.unitins.tp1.dto.UsuarioDTOResponse;

public interface JwtService {
    public String generateJwt(UsuarioDTOResponse dto);
}