package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.request.AddressRequest;
import br.com.pedrohbhrj.DTO.request.AddressUpdateRequest;
import br.com.pedrohbhrj.DTO.response.AddressResponse;
import br.com.pedrohbhrj.controllers.docs.AddressDocs;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.services.interf.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/address")
@RequiredArgsConstructor
public class AddressController implements AddressDocs {

    private final AddressService addressService;


    @Override
    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@AuthenticationPrincipal User user, @RequestBody @Valid AddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(user, request));
    }
    @Override
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@AuthenticationPrincipal User user,@PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.findAddressById(user,addressId));
    }
    @Override
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@AuthenticationPrincipal User user,@PathVariable Long addressId, @RequestBody AddressUpdateRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(user,addressId, request));
    }
    @Override
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal User user,@PathVariable Long addressId) {

        addressService.deleteAddress(user,addressId);

        return ResponseEntity.noContent().build();
    }

}
