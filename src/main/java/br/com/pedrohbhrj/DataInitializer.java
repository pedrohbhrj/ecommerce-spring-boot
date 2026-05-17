package br.com.pedrohbhrj;

import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.models.enums.Role;
import br.com.pedrohbhrj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Value("${email.admin}")
    private String emailAdmin;
    @Value("${password.admin}")
    private String passwordAdmin;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.existsByEmail(emailAdmin)) {
            log.info("admin already registered");
            return;
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(passwordAdmin));
        user.setEmail(emailAdmin);
        user.setRoles(Set.of(Role.ROLE_ADMIN));

        userRepository.save(user);

        log.info("admin saved");

    }
}
