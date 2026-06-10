package br.unitins.tp1.service;

import br.unitins.tp1.dto.UsuarioDTO;

public interface KeycloakUserProvisioningService {

    void createUser(UsuarioDTO dto, String senha);

    void deleteUser(String username);
}
