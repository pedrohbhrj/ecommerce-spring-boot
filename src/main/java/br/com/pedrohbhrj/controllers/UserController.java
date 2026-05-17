package br.com.pedrohbhrj.controllers;

import br.com.pedrohbhrj.DTO.request.UserUpdateRequest;
import br.com.pedrohbhrj.DTO.response.UserResponse;
import br.com.pedrohbhrj.controllers.docs.UserDocs;
import br.com.pedrohbhrj.models.User;
import br.com.pedrohbhrj.services.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserDocs {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal User user ,@RequestBody UserUpdateRequest updateRequest){
        return ResponseEntity.ok(userService.updateUser(user.getId(),updateRequest));
    }
    @GetMapping
    public ResponseEntity<UserResponse> findUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

}
