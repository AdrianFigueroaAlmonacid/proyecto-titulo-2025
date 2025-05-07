package com.food_easy_back.backend_food_easy.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dto.OwnerCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserDto;
import com.food_easy_back.backend_food_easy.model.dto.UserListDto;
import com.food_easy_back.backend_food_easy.model.dto.UserPasswordDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.IUserService;



@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;

    
    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //AQUI ESTAN LOS GET

    //Endpoint para recuperar el usuario de la sesion actual, ver tu perfil,
    @GetMapping
    public ResponseEntity<?> getUser(){
       try {
        UserEntity user = userService.findByUsername(getCurrentUsername());
        UserDto userdto = UserDto.builder()
                                .IdUser(user.getIdUser())
                                .name(user.getName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .username(user.getUsername())
                                .store(user.getStore().getName())
                                .role(userService.setPrivileges(user.getRoles()))
                                .build();
        ResponseMessage response = ResponseMessage.builder()
                                .message("Usuario recuperado correctamente")
                                .object(userdto)
                                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build());
        
       } catch (Exception e) {
        ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar usuario")
                                .object(null)
                                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

       }
    }

    //Endpoint para recuperar todos los usuarios de un negocio
    @GetMapping("/store")
    public ResponseEntity<?> getUsersBusiness(Pageable pageable){
       try {

        Page<UserEntity> usersPage = userService.getUsersByStore( pageable);
        Page<UserListDto> dtoPage = usersPage.map(u -> UserListDto.builder()
                                        .IdUser(u.getIdUser())
                                        .name(u.getName())
                                        .position(userService.setPrivileges(u.getRoles()))
                                        .admin(userService.setPrivileges(u.getRoles())=="ADMIN" ||userService.setPrivileges(u.getRoles())=="OWNER" ? true: false)
                                        .email(u.getEmail())
                                        .username(u.getUsername())
                                        .lastname(u.getLastName())
                                        .registerDate(u.getRegisterDate())
                                        .build());
        ResponseMessage response = ResponseMessage.builder()
                                .message("Usuarios recuperados correctamente")
                                .object(dtoPage)
                                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
        
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        
       } catch (Exception e) {
        ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar usuarios")
                                .object(null)
                                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

       }
    }

    //AQUI ESTAN LOS POST 

    //Endpoint para los owner y admin que crean usuarios
    @PostMapping
    public ResponseEntity<?> createUserByAdmin(@RequestBody UserCreateDto userDto){

        try {

            UserEntity user = userService.saveUserByAdmin(userDto);
            UserDto userdto = UserDto.builder()
                                .username(user.getUsername())
                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                        .message("Usuario creado con exito")
                                        .object(userdto)
                                        .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear usuario: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    //Endpoint para el admin_system que crea negocios y owner
    @PostMapping("/admin")
    public ResponseEntity<?> createOwner(@RequestBody OwnerCreateDto requestDto){
        try {

                UserEntity user = userService.saveOwner(requestDto);
                UserDto userdto = UserDto.builder()
                                    .username(user.getUsername())
                                    .lastName(user.getLastName())
                                    .build();
                ResponseMessage response = ResponseMessage.builder()
                                            .message("Usuario y negocio creado con exito")
                                            .object(userdto)
                                            .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear owner: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }   
    }

    //AQUI ESTAN LOS PUT

    //Endpoint para modificar usuario
    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserUpdateDto userDto){

        try {      
            UserEntity user = userService.updateUser(userDto);
            UserDto userdto = UserDto.builder()
                                .username(user.getUsername())
                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                        .message("Usuario modificado con exito")
                                        .object(userdto)
                                        .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al modificar usuario: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @PutMapping("/password")
    public ResponseEntity<?> modifyUserPassword(@RequestBody UserPasswordDto userDto){

        try {      
            UserEntity user = userService.updateUserPassword(userDto);
            UserDto userdto = UserDto.builder()
                                .username(user.getUsername())
                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                        .message("Usuario modificado con exito")
                                        .object(userdto)
                                        .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al modificar usuario: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    //AQUI ESTAN LOS DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        try { 
            userService.delete(id);     
            ResponseMessage response = ResponseMessage.builder()
                                        .message("Usuario eliminado con exito")
                                        .object(null)
                                        .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al eliminar: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
