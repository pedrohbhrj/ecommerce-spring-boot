package br.com.backend.ecommerce.service.interf;

import br.com.backend.ecommerce.dto.ApiResponse;
import br.com.backend.ecommerce.dto.request.UsuarioAttRequest;
import br.com.backend.ecommerce.dto.response.EnderecoResponse;
import br.com.backend.ecommerce.dto.response.UsuarioResponse;
import br.com.backend.ecommerce.model.Endereco;
import br.com.backend.ecommerce.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario usuarioLogado();
    ApiResponse<EnderecoResponse> atualizarEndereco(String tipoEndereco, Endereco endereco);
    ApiResponse<List<EnderecoResponse>> meusEnderecos();
    ApiResponse<UsuarioResponse> atualizarDados(UsuarioAttRequest request);
}
