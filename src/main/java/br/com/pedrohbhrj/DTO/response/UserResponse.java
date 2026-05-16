package br.com.pedrohbhrj.DTO.response;


import java.util.List;

public record UserResponse(Long id,
                           String email,
                           List<AddressResponse> addressList) {
}
