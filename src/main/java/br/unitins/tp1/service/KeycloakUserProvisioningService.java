package br.unitins.tp1.service;

import br.unitins.tp1.dto.UsuarioDTO;

public interface KeycloakUserProvisioningService {

    void createUser(UsuarioDTO dto);

    void deleteUser(String username);
}
