package br.com.backend.ecommerce.service.interf;

import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.AuthRequest;
import br.com.backend.ecommerce.dto.response.AuthResponse;

public interface AuthService {

    ApiResponse<AuthResponse> registrar(AuthRequest request);
    ApiResponse<AuthResponse> logar(AuthRequest request);
}
