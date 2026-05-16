package br.com.pedrohbhrj.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(@NotBlank(message = "Should not be empty") String name,
                                    Long parentId) {
}
