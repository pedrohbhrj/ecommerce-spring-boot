package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.models.User;

public interface AddressService {

    AddressResponse findAddressById(Long addressId);

    AddressResponse createAddress(User user, AddressRequest request);

    AddressResponse updateAddress(Long addressId, AddressUpdateRequest request);

    void deleteAddress(Long addressId);

}
