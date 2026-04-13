package br.com.backend.ecommerce.dto;

import java.time.Instant;


public record JWTUsuario(String id,
                         String email,
                         String role,
                         Instant dataDeExpiracao) {
}
