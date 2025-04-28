package com.food_easy_back.backend_food_easy.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dao.StoreDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.OwnerCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.StoreCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserPasswordDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;
import com.food_easy_back.backend_food_easy.service.IStoreService;
import com.food_easy_back.backend_food_easy.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;
    private final StoreDao storeDao;
    private final PasswordEncoder passwordEncoder;
    private final IStoreService storeService;

    
    @Autowired
    public UserServiceImpl(UserDao userDao, StoreDao storeDao, PasswordEncoder passwordEncoder,
            IStoreService storeService) {
        this.userDao = userDao;
        this.storeDao = storeDao;
        this.passwordEncoder = passwordEncoder;
        this.storeService = storeService;
    }


    //Metodo para guardar el usuario desde el owner
    @Transactional
    @Override
    public UserEntity saveUserByAdmin(UserCreateDto userdto) {

        if(existUsername(userdto.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe" );
        }else{
            userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
            UserEntity admin= findByUsername(getCurrentUsername());
            Integer store= admin.getStore().getIdStore();
            userdto.setStore(store);
            StoreEntity storeEntity = storeDao.findById(userdto.getStore()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store no encontrado"));
            UserEntity user = UserEntity.builder()
                            .name(userdto.getName())
                            .lastName(userdto.getLastname())
                            .email(userdto.getEmail())
                            .password(userdto.getPassword())
                            .username(userdto.getUsername())
                            .store(storeEntity)
                            .disabled(false)
                            .locked(false)
                            .build();
            List<UserRoleEntity> userRoles = userdto.getRoles().stream()
                                                    .map(role -> {
                                                        UserRoleEntity userRole = new UserRoleEntity();
                                                        userRole.setUsername(user.getUsername());
                                                        userRole.setRole(role);
                                                        userRole.setGrantedDate(LocalDateTime.now());
                                                        userRole.setUser(user); 
                                                        return userRole;
                                                    })
                                                    .collect(Collectors.toList());

            user.setRoles(userRoles);
            return userDao.save(user);
        }
    }

    //Metodo para guardar el usuario owner y el negocio desde el admin
    @Transactional
    @Override
    public UserEntity saveOwner(OwnerCreateDto requestDto) {
        UserCreateDto userDto = requestDto.getUser();
        StoreCreateDto storeDto = requestDto.getStore();
        if(existUsername(userDto.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe" );
        }

        StoreEntity storeEntity = storeService.saveStore(storeDto);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Integer store= storeEntity.getIdStore();
        userDto.setStore(store);

        userDto.setRoles(List.of("OWNER", "ADMIN", "USER"));

        UserEntity user = UserEntity.builder()
                         .name(userDto.getName())
                         .lastName(userDto.getLastname())
                         .email(userDto.getEmail())
                         .password(userDto.getPassword())
                         .username(userDto.getUsername())
                         .store(storeEntity)
                         .disabled(false)
                         .locked(false)
                         .build();
        List<UserRoleEntity> userRoles = userDto.getRoles().stream()
                                                .map(role -> {
                                                    UserRoleEntity userRole = new UserRoleEntity();
                                                    userRole.setUsername(user.getUsername());
                                                    userRole.setRole(role);
                                                    userRole.setGrantedDate(LocalDateTime.now());
                                                    userRole.setUser(user); 
                                                    return userRole;
                                                })
                                                .collect(Collectors.toList());

        user.setRoles(userRoles);
        return userDao.save(user);
    }

    //Metodo para actualizar un usuario 
    @Transactional
    @Override
    public UserEntity updateUser(UserUpdateDto userdto) {

        if(!existUsername(userdto.getUsername()) || findById(userdto.getId()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado" );
        }
        UserEntity currentUser = findByUsername(getCurrentUsername());
        UserEntity user = findById(userdto.getId());

        boolean stores = user.getStore().equals(currentUser.getStore());

        String currentUserRole = setPrivileges(currentUser.getRoles());
        String targetUserRole = setPrivileges(user.getRoles());

        UserEntity userFinal = UserEntity.builder()
                         .idUser(userdto.getId())
                         .name(userdto.getName())
                         .lastName(userdto.getLastName())
                         .email(userdto.getEmail())
                         .username(userdto.getUsername())
                         .password(user.getPassword())
                         .store(user.getStore())
                         .disabled(false)
                         .locked(false)
                         .build();

        if(targetUserRole.equals("ADMIN_SYSTEM")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un administrador del sistema." );
        }

        if(currentUserRole.equals("ADMIN_SYSTEM")){
            return userDao.save(userFinal);
        }else if(targetUserRole.equals("ADMIN") && currentUserRole.equals("OWNER") ){
            if(stores){
                return userDao.save(userFinal);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un usuario de otro negocio." );
            }
        }else if(targetUserRole.equals("USER") && !currentUserRole.equals("USER")){
            if(stores){
                return userDao.save(userFinal);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un usuario de otro negocio." );
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar con los privilegios actuales." );
        }

    }

    @Override
    public UserEntity updateUserPassword(UserPasswordDto userdto) {
        if(!existUsername(userdto.getUsername()) || findById(userdto.getId()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado" );
        }
        UserEntity currentUser = findByUsername(getCurrentUsername());
        UserEntity user = findById(userdto.getId());

        boolean stores = user.getStore().equals(currentUser.getStore());

        String currentUserRole = setPrivileges(currentUser.getRoles());
        String targetUserRole = setPrivileges(user.getRoles());

        user.setPassword(passwordEncoder.encode(userdto.getPassword()));

        UserEntity userFinal = UserEntity.builder()
                         .idUser(userdto.getId())
                         .name(user.getName())
                         .lastName(user.getLastName())
                         .email(user.getEmail())
                         .username(userdto.getUsername())
                         .password(user.getPassword())
                         .store(user.getStore())
                         .disabled(false)
                         .locked(false)
                         .build();

        if(targetUserRole.equals("ADMIN_SYSTEM")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un administrador del sistema." );
        }

        if(currentUserRole.equals("ADMIN_SYSTEM")){
            return userDao.save(userFinal);
        }else if(targetUserRole.equals("ADMIN") && currentUserRole.equals("OWNER") ){
            if(stores){
                return userDao.save(userFinal);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un usuario de otro negocio." );
            }
        }else if(targetUserRole.equals("USER") && !currentUserRole.equals("USER")){
            if(stores){
                return userDao.save(userFinal);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar un usuario de otro negocio." );
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede modificar con los privilegios actuales." );
        }
    }


    //Metodo para encontrar un usuario por id
    @Transactional
    @Override
    public UserEntity findById(Integer id) {
        return userDao.findById(id).orElse(null);
        
    }

    //Metodo para eliminar un usuario y da permiso de acuerdo a los roles
    @Transactional
    @Override
    public void delete(Integer id) {
        UserEntity currentUser = findByUsername(getCurrentUsername());
        UserEntity user = findById(id);
        if (user==null || currentUser==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado para ser eliminado." );
        }
        String currentUserRole = setPrivileges(currentUser.getRoles());
        String targetUserRole = setPrivileges(user.getRoles());


        if(targetUserRole.equals("ADMIN_SYSTEM")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un administrador del sistema." );
        }
        boolean stores = user.getStore().equals(currentUser.getStore());

        if(currentUserRole.equals("ADMIN_SYSTEM")){
            userDao.delete(user);
        }else if(targetUserRole.equals("ADMIN") && currentUserRole.equals("OWNER") ){
            if(stores){
                userDao.delete(user);
                return;
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un usuario de otro negocio." );
            }
        }else if(targetUserRole.equals("USER") && !currentUserRole.equals("USER")){
            if(stores){
                userDao.delete(user);
                return;
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar un usuario desde otro usuario." );
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar con los privilegios actuales." );
        }
    }

    //Metodo para verificar si un usuario existe por su username
    @Transactional
    @Override
    public boolean existUsername(String username){
        if(userDao.findByUsername(username).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    //Metodo para encontrar un usuario por su username
    @Override
    public UserEntity findByUsername(String username) {
         UserEntity user = userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return user;
    }

    //Metodo para obtener la paginacion de usuarios
    @Override
    public Page<UserEntity> getUsersByStore( Pageable page) {
        UserEntity user = findByUsername(getCurrentUsername());
        if (user==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado" );
        }
        return userDao.findAllByStore(user.getStore(), page);
    }


    //Metodo que devuelve el usuario actual de la peticion
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    //Metodo que devuelve el rol dominante 
    @Override
    public String setPrivileges(List<UserRoleEntity> roles){
        List<String> roleNames = new ArrayList<>();

        for(UserRoleEntity rol: roles){
            roleNames.add(rol.getRole());
        }
        if(roleNames.contains("ADMIN_SYSTEM")){
            return "ADMIN_SYSTEM";
        }else if(roleNames.contains("OWNER")){
            return "OWNER";
        }else if (roleNames.contains("ADMIN")){
            return "ADMIN";
        }
        return "USER";
    }



}
