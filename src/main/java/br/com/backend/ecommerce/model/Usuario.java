package br.com.backend.ecommerce.model;

import br.com.backend.ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nomeCompleto;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true)
    private String cpf;

    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "vendedor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private Role role = Role.CLIENTE;

    @Column(nullable = false,updatable = false)
    @Builder.Default
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime atualizadoEm = LocalDateTime.now();


    @PrePersist
    private void prePersist(){
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }
    @PreUpdate
    private void preUpdate(){
        this.atualizadoEm = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
