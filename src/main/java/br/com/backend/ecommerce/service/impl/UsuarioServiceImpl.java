package br.com.backend.ecommerce.service.impl;

import br.com.backend.ecommerce.dto.request.UsuarioAttRequest;
import br.com.backend.ecommerce.dto.response.UsuarioResponse;
import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.EnderecoRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.exception.NotFoundException;
import br.com.backend.ecommerce.mapper.EnderecoMapper;
import br.com.backend.ecommerce.mapper.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper, UsuarioAuthAuxiliar usuarioAuthAuxiliar, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.usuarioAuthAuxiliar = usuarioAuthAuxiliar;
        this.usuarioMapper = usuarioMapper;
    }


    @Override
    public ApiResponse<List<EnderecoResponse>> meusEnderecos(UUID idUsuario) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuarioEncontrado = usuarioAuthAuxiliar.encontrarUsuario(usuarioRepository,idUsuario);

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

        Usuario usuario = usuarioAuthAuxiliar.encontrarUsuario(usuarioRepository,idUsuario);

        Endereco endereco = enderecoMapper.toEntity(request);

        endereco.setCliente(usuario);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        for(Endereco enderecos:usuario.getEnderecos()){
            if(!enderecos.getId().equals(enderecoSalvo.getId())){
                enderecos.setPrincipal(false);
                enderecos.setAtivo(true);
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

        Usuario usuarioEncontrado = usuarioAuthAuxiliar.encontrarUsuario(usuarioRepository,idUsuario);

        usuarioEncontrado
                .getEnderecos()
                .forEach(e -> {
                    if(e.getId().equals(enderecoEncontrado.getId())){
                        e.setPrincipal(false);
                        e.setAtivo(true);
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

    @Override
    public ApiResponse<UsuarioResponse> atualizarInformacoesDeUsuario(UUID idUsuario, UsuarioAttRequest request) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuarioEncontrado = usuarioAuthAuxiliar.encontrarUsuario(usuarioRepository,idUsuario);

        usuarioMapper.mergeUsuario(usuarioEncontrado,request);

        return new ApiResponse<>("Usuario atualizado com sucesso.",200,usuarioMapper.toRes(usuarioEncontrado));
    }

    @Override
    public ApiResponse<UsuarioResponse> encontrarUsuario(UUID idUsuario){
        return new ApiResponse<>("Usuario encontrado com sucesso.",200,usuarioMapper.toRes(usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuario não encontrado."))));
    }

    @Override
    public ApiResponse<Void> deleteUsuario(UUID idUsuario) {

        usuarioAuthAuxiliar.validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuario = usuarioAuthAuxiliar.encontrarUsuario(usuarioRepository,idUsuario);

        usuarioRepository.delete(usuario);

        return new ApiResponse<>("Usuario deletado com sucesso.",200,null);
    }

    @Override
    public ApiResponse<EnderecoResponse> enderecoEntrega() {
        Endereco enderecoEncontrado = enderecoRepository.findByPrincipal(true).orElseThrow(() -> new NotFoundException("Não existe endereco nessa conta , por favor cadastre um."));
        return new ApiResponse<>("Endereço de entrega encontrado.",200,enderecoMapper.toRes(enderecoEncontrado));
    }

}
