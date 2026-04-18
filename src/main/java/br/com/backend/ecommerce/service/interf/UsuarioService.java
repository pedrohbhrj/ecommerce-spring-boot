package br.com.backend.ecommerce.service.interf;

import br.com.backend.ecommerce.dto.request.UsuarioAttRequest;
import br.com.backend.ecommerce.dto.response.UsuarioResponse;
import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.EnderecoRequest;

import br.com.backend.ecommerce.dto.response.EnderecoResponse;

import br.com.backend.ecommerce.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
    ApiResponse<List<EnderecoResponse>> meusEnderecos(UUID idUsuario);
    ApiResponse<EnderecoResponse> criarEndereco(UUID idUsuario,EnderecoRequest request);
    ApiResponse<EnderecoResponse> atualizarEnderecoComoPrincipal(UUID idUsuario,UUID idEndereco);
    ApiResponse<UsuarioResponse> atualizarInformacoesDeUsuario(UUID idUsuario, UsuarioAttRequest request);

    ApiResponse<UsuarioResponse> encontrarUsuario(UUID idUsuario);
    ApiResponse<Void> deleteUsuario(UUID idUsuario);
    ApiResponse<EnderecoResponse> enderecoEntrega();

}
