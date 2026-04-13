package br.com.backend.ecommerce.config;


import br.com.backend.ecommerce.dto.JWTUsuario;
import br.com.backend.ecommerce.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTConfig jwtConfig;
    private final UserConfig userConfig;

    public SecurityFilter(JWTConfig jwtConfig, UserConfig userConfig) {
        this.jwtConfig = jwtConfig;
        this.userConfig = userConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(Strings.isNotEmpty(authorization) && authorization.startsWith("Bearer ")){

            String token = authorization.substring(7);

            Optional<JWTUsuario> jwtUsuario = jwtConfig.validateToken(token);
            if(jwtUsuario.isPresent()){
                JWTUsuario usuario = jwtUsuario.get();

                UserDetails usuarioDetails = userConfig.loadUserByUsername(usuario.email());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuarioDetails,null,usuarioDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }

        }
        doFilter(request,response,filterChain);
    }
}
