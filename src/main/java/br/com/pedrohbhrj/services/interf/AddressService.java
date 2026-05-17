package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.models.User;

public interface AddressService {

    AddressResponse findAddressById(User user,Long addressId);

    AddressResponse createAddress(User user, AddressRequest request);

    AddressResponse updateAddress(User user,Long addressId, AddressUpdateRequest request);

    void deleteAddress(User user,Long addressId);

}
