package br.com.backend.ecommerce.service.impl;

import br.com.backend.ecommerce.config.JWTConfig;
import br.com.backend.ecommerce.exception.ApiResponse;
import br.com.backend.ecommerce.dto.request.AuthRequest;
import br.com.backend.ecommerce.dto.response.AuthResponse;
import br.com.backend.ecommerce.enums.Role;
import br.com.backend.ecommerce.exception.AlreadyExistsException;
import br.com.backend.ecommerce.exception.NotFoundException;
import br.com.backend.ecommerce.model.Usuario;
import br.com.backend.ecommerce.repository.UsuarioRepository;
import br.com.backend.ecommerce.service.interf.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UsuarioRepository repository;
    private final JWTConfig jwtConfig;
    private final AuthenticationManager manager;

    public AuthServiceImpl(PasswordEncoder encoder, UsuarioRepository repository, JWTConfig jwtConfig, AuthenticationManager manager) {
        this.encoder = encoder;
        this.repository = repository;
        this.jwtConfig = jwtConfig;
        this.manager = manager;
    }


    @Override
    @Transactional
    public ApiResponse<AuthResponse> registrar(AuthRequest request) {
        if(repository.existsByEmail(request.email())) throw new AlreadyExistsException("Já existe esse email no sistema tente outro.");
        Usuario usuario = Usuario
                .builder()
                .email(request.email())
                .senha(encoder.encode(request.senha()))
                .build();
        if(request.role() == null){
            usuario.setRole(Role.CLIENTE);
        }
        Usuario usuarioSalvo = repository.save(usuario);
        return new ApiResponse<>(
                "Usuario registrado com sucesso.",
                HttpStatus.CREATED.value(),
                new AuthResponse(usuarioSalvo.getEmail(),null)
        );
    }

    @Override
    public ApiResponse<AuthResponse> logar(AuthRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(),request.senha());
        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        if(usuario == null) throw new NotFoundException("Usuario não registrado.");

        String token = jwtConfig.generateToken(usuario);
        return new ApiResponse<>("Usuario logado com sucesso.",HttpStatus.CREATED.value(), new AuthResponse(null,token));
    }
}
