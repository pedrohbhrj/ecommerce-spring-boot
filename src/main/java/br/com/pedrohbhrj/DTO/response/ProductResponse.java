package br.com.pedrohbhrj.DTO.response;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              Integer stockQuantity,
                              String categoryName,
                              String imgUrl,
                              BigDecimal price) {
}
