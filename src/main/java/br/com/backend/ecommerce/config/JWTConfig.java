package br.com.backend.ecommerce.config;

import br.com.backend.ecommerce.dto.JWTUsuario;
import br.com.backend.ecommerce.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTConfig {

    private final String secret = "secret";


    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userId", usuario.getId().toString())
                .withSubject(usuario.getEmail())
                .withClaim("role",usuario.getRole().toString())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .sign(algorithm);
    }
    public Optional<JWTUsuario> validateToken(String token){
        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);

            return Optional.of(new JWTUsuario(decodedJWT.getClaim("userId").toString(),decodedJWT.getSubject(),decodedJWT.getClaim("role").toString(),decodedJWT.getExpiresAtAsInstant()));
        }catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
