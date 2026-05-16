package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.mapper.AddressMapper;
import br.com.pedrohbhrj.models.Address;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.repository.AddressRepository;
import br.com.pedrohbhrj.services.interf.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional(readOnly = true)
    public AddressResponse findAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found."));

        log.info("Address was found.");

        return  addressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public AddressResponse createAddress(User user, AddressRequest request) {

        Address address = addressMapper.toEntity(request);

        address.setUser(user);

        Address addressSaved = addressRepository.save(address);

        log.info("Address created successfully, with email: {}", user.getEmail());

        return addressMapper.toResponse(addressSaved);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long addressId, AddressUpdateRequest request) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found."));

        addressMapper.mergeAddress(request,address);

        Address addressSaved = addressRepository.save(address);

        log.info("Address updated successfully");

        return addressMapper.toResponse(addressSaved);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found."));

        addressRepository.delete(address);

    }
}
