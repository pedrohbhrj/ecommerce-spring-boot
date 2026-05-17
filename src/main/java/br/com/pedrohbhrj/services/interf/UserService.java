package br.com.pedrohbhrj.services.interf;

import br.com.pedrohbhrj.DTO.request.UserUpdateRequest;
import br.com.pedrohbhrj.DTO.response.UserResponse;

public interface UserService {

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest request);

}
