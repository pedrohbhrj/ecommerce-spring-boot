package br.com.backend.ecommerce.service.impl;

import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.EnderecoRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.exception.NotFoundException;
import br.com.backend.ecommerce.mapper.EnderecoMapper;
import br.com.backend.ecommerce.model.Endereco;
import br.com.backend.ecommerce.model.Usuario;
import br.com.backend.ecommerce.repository.EnderecoRepository;
import br.com.backend.ecommerce.repository.UsuarioRepository;
import br.com.backend.ecommerce.service.interf.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final UsuarioAuthAuxiliar usuarioAuthAuxiliar;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper, UsuarioAuthAuxiliar usuarioAuthAuxiliar) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.usuarioAuthAuxiliar = usuarioAuthAuxiliar;
    }


    @Override
    @Transactional
    public ApiResponse<List<EnderecoResponse>> meusEnderecos(UUID idUsuario) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuarioEncontrado = encontrarUsuario(idUsuario);

        return new ApiResponse<>(
                "Endereços encontrados com sucesso.",
                HttpStatus.OK.value(),
                enderecoRepository
                        .findAllByClienteId(usuarioEncontrado.getId())
                        .stream()
                        .map(enderecoMapper::toRes)
                        .toList()
        );
    }
    @Override
    @Transactional
    public ApiResponse<EnderecoResponse> criarEndereco(UUID idUsuario,EnderecoRequest request) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuario = encontrarUsuario(idUsuario);

        Endereco endereco = enderecoMapper.toEntity(request);

        endereco.setCliente(usuario);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        for(Endereco enderecos:usuario.getEnderecos()){
            if(!enderecos.getId().equals(enderecoSalvo.getId())){
                enderecos.setPrincipal(false);
                enderecos.setAtivo(false);
            }
        }

        usuarioRepository.save(usuario);

        return new ApiResponse<>(
                "Endereço criado com sucesso.",
                HttpStatus.CREATED.value(),
                enderecoMapper.toRes(enderecoSalvo)
        );
    }

    @Override
    @Transactional
    public ApiResponse<EnderecoResponse> atualizarEnderecoComoPrincipal(UUID idUsuario ,UUID idEndereco) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Endereco enderecoEncontrado =
                enderecoRepository
                .findById(idEndereco).orElseThrow(() -> new NotFoundException("Endereco não encontrado."));

        Usuario usuarioEncontrado = encontrarUsuario(idUsuario);

        usuarioEncontrado
                .getEnderecos()
                .forEach(e -> {
                    if(e.getId().equals(enderecoEncontrado.getId())){
                        e.setPrincipal(false);
                        e.setAtivo(false);
                    }
                });

        Usuario usuarioSalvo = usuarioRepository.save(usuarioEncontrado);

        Endereco enderecoResponse = null;

        for(Endereco enderecos:usuarioSalvo.getEnderecos()){
            if(enderecos.getId().equals(enderecoEncontrado.getId())){
                enderecoResponse = enderecos;
            }
        }

        return new ApiResponse<>("Endereço atualizado com sucesso como principal.",
                HttpStatus.OK.value(),
                enderecoMapper.toRes(enderecoResponse)
        );
    }

    public Usuario encontrarUsuario(UUID idUsuario){
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuario não encontrado."));
    }

}
