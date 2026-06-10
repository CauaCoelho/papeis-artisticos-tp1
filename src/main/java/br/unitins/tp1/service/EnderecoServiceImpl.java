package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.EnderecoDTO;
import br.unitins.tp1.dto.EnderecoDTOResponse;
import br.unitins.tp1.model.Endereco;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.EnderecoRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public EnderecoDTOResponse findByCep(String cep) {
        Endereco endereco = repository.find("cep = ?1", cep).firstResult();
        if (endereco == null) {
            throw new WebApplicationException("Endereço não encontrado", Status.NOT_FOUND);
        }
        return EnderecoDTOResponse.valueOf(endereco);
    }

    @Override
    public EnderecoDTOResponse create(EnderecoDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado", Status.NOT_FOUND);
        }

        Endereco endereco = new Endereco();
        endereco.setUsuario(usuario);
        mapearCampos(endereco, dto);

        repository.persist(endereco);
        return EnderecoDTOResponse.valueOf(endereco);
    }

    @Override
    public void update(Long id, EnderecoDTO dto) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new WebApplicationException("Endereço não encontrado", Status.NOT_FOUND);
        }
        mapearCampos(endereco, dto);
    }

    @Override
    public List<EnderecoDTOResponse> findByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(EnderecoDTOResponse::valueOf)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new WebApplicationException("Endereço não encontrado", Status.NOT_FOUND);
        }
        repository.delete(endereco);
    }

    private void mapearCampos(Endereco endereco, EnderecoDTO dto) {
        endereco.setCep(dto.cep());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
    }
}
