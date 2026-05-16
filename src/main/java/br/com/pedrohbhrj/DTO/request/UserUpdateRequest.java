package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record UserUpdateRequest (@Email(message = "Format invalid.") @NotBlank String email,
                                 @NotBlank(message = "Should not be empty.") String password){
}
