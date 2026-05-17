package br.com.pedrohbhrj.config;

import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.services.JWTUserData;
import br.com.pedrohbhrj.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsImpl userDetailsImpl;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizedHeader = request.getHeader("Authorization");

        if(Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {
            String token = authorizedHeader.substring(7);
            Optional<JWTUserData> userJwt = tokenService.validateToken(token);

            if (userJwt.isPresent()) {
                JWTUserData userOpt = userJwt.get();
                User user = (User) userDetailsImpl.loadUserByUsername(userOpt.email());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("User authenticated ,email: {} , token: {}",user.getEmail(),token);
            }
        }
        filterChain.doFilter(request,response);
    }
}
