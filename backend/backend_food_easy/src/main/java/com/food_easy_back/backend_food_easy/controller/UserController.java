package com.food_easy_back.backend_food_easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_easy_back.backend_food_easy.model.dto.OwnerCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.StoreCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.IStoreService;
import com.food_easy_back.backend_food_easy.service.IUserService;



@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IStoreService storeService;

    
    @Autowired
    public UserController(IUserService userService, PasswordEncoder passwordEncoder, IStoreService storeService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<?> getUser(){
       try {
        UserEntity user = userService.findByUsername(getCurrentUsername());
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
    @GetMapping("/business")
    public ResponseEntity<?> getUsersBusiness(){
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

    //Metodo para los owner que crean usuarios
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDto userDto){

        try {
            
            if (userService.existUsername(userDto.getUsername())){
                ResponseMessage error = ResponseMessage.builder()
                                    .message("Usuario esta actualmente registrado")
                                    .object(null)
                                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }else{
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity admin= userService.findByUsername(getCurrentUsername());
                Integer store= admin.getStore().getIdStore();
                userDto.setStore(store);
                UserEntity user = userService.saveUser(userDto);
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

    //Metodo para el admin que crea negocios y owner
    @PostMapping("/owner")
    public ResponseEntity<?> createOwner(@RequestBody OwnerCreateRequestDto requestDto){



        try {
            UserCreateRequestDto userDto = requestDto.getUser();
            StoreCreateRequestDto storeDto = requestDto.getStore();
            System.out.println(requestDto.getUser().getLastname());
            
            if (userService.existUsername(userDto.getUsername())){
                ResponseMessage error = ResponseMessage.builder()
                                    .message("Usuario esta actualmente registrado")
                                    .object(null)
                                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }else if(storeService.existStore(storeDto.getName())){
                ResponseMessage error = ResponseMessage.builder()
                                    .message("Negocio esta actualmente registrado")
                                    .object(null)
                                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }else{
                
                StoreEntity storeEntity = storeService.saveStore(storeDto);
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                Integer store= storeEntity.getIdStore();
                userDto.setStore(store);
                UserEntity user = userService.saveUser(userDto);
                UserDto userdto = UserDto.builder()
                                    .username(user.getUsername())
                                    .lastName(user.getLastName())
                                    .build();
                ResponseMessage response = ResponseMessage.builder()
                                            .message("Usuario y negocio creado con exito")
                                            .object(userdto)
                                            .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
            }
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear owner: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }   
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserUpdateRequestDto userDto){

        try {      
            if (userService.existUsername(userDto.getUsername())){
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity user = userService.updateUser(userDto);
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

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
