package br.com.pedrohbhrj.services;


import java.util.List;

public record JWTUserData(Long userId, String email, List<String> roles) {
}
