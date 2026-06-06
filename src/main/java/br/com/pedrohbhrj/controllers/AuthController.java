package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.request.LoginRequest;
import br.com.pedrohbhrj.DTO.request.RegisterUserRequest;
import br.com.pedrohbhrj.DTO.response.LoginResponse;
import br.com.pedrohbhrj.DTO.response.RegisterUserResponse;
import br.com.pedrohbhrj.controllers.docs.AuthDocs;
import br.com.pedrohbhrj.exceptions.AlreadyExistsException;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.models.enums.Role;
import br.com.pedrohbhrj.repository.UserRepository;
import br.com.pedrohbhrj.services.auth.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequestMapping("/api/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthDocs {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        if (repository.existsByEmail(request.email())) throw new AlreadyExistsException("Already exists this email.");
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(Role.ROLE_USER));
        repository.save(user);
        log.info("User saved successfully, id: {}", user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        log.info("User logged successfully, id: {}", user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }


}
