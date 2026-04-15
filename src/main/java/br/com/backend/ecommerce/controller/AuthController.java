package br.com.backend.ecommerce.controller;

import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.AuthRequest;
import br.com.backend.ecommerce.dto.response.AuthResponse;
import br.com.backend.ecommerce.service.interf.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<AuthResponse>> registrar(@RequestBody @Valid AuthRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }
    @PostMapping("/logar")
    public ResponseEntity<ApiResponse<AuthResponse>> logar(@RequestBody @Valid AuthRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.logar(request));
    }
}
