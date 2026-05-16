package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest (@NotNull @NotBlank(message = "Should not be empty") @Size(max = 120,message = "Max 120 characters") String name,
                               Long parentId){
}
