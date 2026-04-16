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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }


    @Override
    public Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new AccessDeniedException("Usuario não está logado, por favor faça login.");
        return (Usuario) authentication.getPrincipal();
    }

    @Override
    @Transactional
    public ApiResponse<List<EnderecoResponse>> meusEnderecos(UUID idUsuario) {

        Usuario usuarioEncontrado = encontrarUsuario(idUsuario);

        validaUsuarioLogadoComEncontrado(idUsuario);

        List<EnderecoResponse> enderecoResponses = new ArrayList<>();

        for(Map.Entry<String,Endereco> entry:usuarioEncontrado.getEndereco().entrySet()){
            EnderecoResponse response = enderecoMapper.toRes(entry.getValue(),entry.getKey());
            enderecoResponses.add(response);
        }



        return new ApiResponse<>(
                "Endereços encontrados com sucesso.",
                HttpStatus.OK.value(),enderecoResponses
        );
    }
    @Override
    @Transactional
    public ApiResponse<EnderecoResponse> criarEndereco(UUID idUsuario,EnderecoRequest request) {

        validaUsuarioLogadoComEncontrado(idUsuario);

        Usuario usuarioEncontrado = encontrarUsuario(idUsuario);
        Endereco endereco = enderecoMapper.toEntity(request);
        usuarioEncontrado.getEndereco().put("principal",endereco);


        Usuario usuarioComEnderecoSalvo = usuarioRepository.save(usuarioEncontrado);


        return new ApiResponse<>(
                "Endereço criado com sucesso.",
                HttpStatus.CREATED.value(),
                enderecoMapper.toRes(usuarioComEnderecoSalvo.getEndereco().get("principal"),
                        "principal")
        );
    }

    @Override
    @Transactional
    public ApiResponse<EnderecoResponse> atualizarEnderecoComoPrincipal(UUID idUsuario ,UUID idEndereco) {

        validaUsuarioLogadoComEncontrado(idUsuario);

        Endereco enderecoEncontrado =
                enderecoRepository
                .findById(idEndereco).orElseThrow(() -> new NotFoundException("Endereco não encontrado."));

        Usuario usuarioEncontrado = encontrarUsuario(idUsuario);

        usuarioEncontrado.getEndereco().put("Entrega",enderecoEncontrado);

        Usuario usuarioSalvo = usuarioRepository.save(usuarioEncontrado);

        return new ApiResponse<>("Endereço atualizado com sucesso como principal.",
                HttpStatus.OK.value(),
                enderecoMapper.toRes(usuarioSalvo.getEnderecoPrincipal(),"principal")
        );
    }

    private void validaUsuarioLogadoComEncontrado(UUID idUsuario){
        Usuario usuarioLogado = usuarioLogado();
        if(!idUsuario.equals(usuarioLogado.getId())){
            throw new AccessDeniedException("Acesso não autorizado a informações.");
        }
    }

    private Usuario encontrarUsuario(UUID idUsuario){
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotFoundException("Usuario não encontrado."));
    }

}
