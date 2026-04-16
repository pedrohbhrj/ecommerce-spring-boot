package br.com.backend.ecommerce.repository;

import br.com.backend.ecommerce.model.Pedido;
import br.com.backend.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<UserDetails> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.endereco WHERE u.id = :idUsuario")
    Optional<Usuario> findByIdEnderecos(@Param("idUsuario") UUID idUsuario);
}
