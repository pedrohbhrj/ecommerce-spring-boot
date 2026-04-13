package br.com.backend.ecommerce.config;

import br.com.backend.ecommerce.exception.NotFoundException;
import br.com.backend.ecommerce.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserConfig implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserConfig(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() -> new NotFoundException("Usuario não encontrado no banco de dados."));
    }
}
