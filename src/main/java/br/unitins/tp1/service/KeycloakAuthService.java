package br.unitins.tp1.service;

import br.unitins.tp1.dto.AuthDTOResponse;
import br.unitins.tp1.dto.AuthUsuarioDTO;

public interface KeycloakAuthService {

    AuthDTOResponse login(AuthUsuarioDTO authDTO);
}
