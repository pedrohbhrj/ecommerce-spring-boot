package br.com.pedrohbhrj.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import br.com.pedrohbhrj.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class TokenService {

    @Value("${jwt.secret.token}")
    private String secret;


    public String generateToken(User user){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT
                .create()
                .withClaim("userId",user.getId())
                .withSubject(user.getEmail())
                .withClaim("roles",user.getRoles().stream().map(Enum::name).toList())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(
                    new JWTUserData(decode.getClaim("userId").asLong(),
                            decode.getSubject(),
                            decode.getClaim("roles").asList(String.class)
                    )
            );
        }catch (JWTVerificationException ex){
            log.debug("User not verified , with token {} ",token);
            return Optional.empty();
        }

    }
}
