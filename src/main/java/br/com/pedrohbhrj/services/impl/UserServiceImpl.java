package br.com.pedrohbhrj.services.impl;

import br.com.pedrohbhrj.DTO.request.UserUpdateRequest;
import br.com.pedrohbhrj.DTO.response.UserResponse;
import br.com.pedrohbhrj.exceptions.NotFoundException;
import br.com.pedrohbhrj.mapper.UserMapper;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.repository.UserRepository;
import br.com.pedrohbhrj.services.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));

        log.info("User found successfully, id: {}",user.getId());

        return new UserResponse(user.getId(),user.getEmail());
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));

        userMapper.mergeUser(request,user);

        User userSaved = userRepository.save(user);

        log.info("User updated successfully , id: {}",user.getId());

        return new UserResponse(
                userSaved.getId(),
                userSaved.getEmail()
        );
    }
}
