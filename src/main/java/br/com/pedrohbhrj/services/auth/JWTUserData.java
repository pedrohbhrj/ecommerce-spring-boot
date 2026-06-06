package br.com.pedrohbhrj.services.auth;


import java.util.List;

public record JWTUserData(Long userId, String email, List<String> roles) {
}
