package br.com.pedrohbhrj.DTO.request;


public record AddressUpdateRequest(String street,
                                   String number,
                                   String zipCode,
                                   String complement,
                                   String neighborhood,
                                   String city,
                                   String state) {
}
