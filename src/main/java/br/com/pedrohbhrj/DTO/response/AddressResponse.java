package br.com.pedrohbhrj.DTO.response;

public record AddressResponse(Long id,
                              String street,
                              String number,
                              String zipCode,
                              String complement,
                              String neighborhood,
                              String city,
                              String state) {
}
