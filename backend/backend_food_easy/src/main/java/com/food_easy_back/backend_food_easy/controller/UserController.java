package com.food_easy_back.backend_food_easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_easy_back.backend_food_easy.model.dto.UserDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.UserServiceImpl.UserServiceImpl;



@RestController
@RequestMapping("/api/vi/user")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserServiceImpl userService ,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id){
       try {
        UserEntity user = userService.findById(id);
        UserDto userdto = UserDto.builder()
                                .name(user.getName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .username(user.getUsername())
                                .build();
        ResponseMessage response = ResponseMessage.builder()
                                .message("Usuario recuperado correctamente")
                                .object(userdto)
                                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        
       } catch (Exception e) {
        ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar usuario")
                                .object(null)
                                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

       }
    }

    @PostMapping
    public ResponseEntity<?> signIn(@RequestBody UserDto userDto){

        try {
            
            if (userService.existUsername(userDto.getUsername())){
                ResponseMessage error = ResponseMessage.builder()
                                    .message("Usuario esta actualmente registrado")
                                    .object(null)
                                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }else{
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity user = userService.save(userDto);
                UserDto userdto = UserDto.builder()
                                    .username(user.getUsername())
                                    .build();
                ResponseMessage response = ResponseMessage.builder()
                                            .message("Usuario creado con exito")
                                            .object(userdto)
                                            .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
            }
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear usuario: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

        
    }
    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserDto userDto){

        try {      
            if (userService.existUsername(userDto.getUsername())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity user = userService.save(userDto);
                UserDto userdto = UserDto.builder()
                                    .username(user.getUsername())
                                    .build();
                ResponseMessage response = ResponseMessage.builder()
                                            .message("Usuario modificado con exito")
                                            .object(userdto)
                                            .build();
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                ResponseMessage error = ResponseMessage.builder()
                .message("Usuario no esta registrado")
                .object(null)
                .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
            }
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear usuario: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        UserEntity user = userService.findById(id);
        if (user!=null){
            userService.delete(user);
        }
    }

}
