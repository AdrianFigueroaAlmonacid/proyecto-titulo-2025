package com.food_easy_back.backend_food_easy.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.StoreDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;
import com.food_easy_back.backend_food_easy.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;
    private final StoreDao storeDao;

    
    @Autowired
    public UserServiceImpl(UserDao userDao, StoreDao storeDao) {
        this.userDao = userDao;
        this.storeDao = storeDao;
    }


    @Transactional
    @Override
    public UserEntity saveUser(UserCreateRequestDto userdto) {

        StoreEntity storeEntity = storeDao.findById(userdto.getStore()).orElseThrow(null);

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
    @Transactional
    @Override
    public UserEntity updateUser(UserUpdateRequestDto userdto) {

        UserEntity user = UserEntity.builder()
                         .name(userdto.getName())
                         .lastName(userdto.getLastName())
                         .email(userdto.getEmail())
                         .password(userdto.getPassword())
                         .username(userdto.getUsername())
                         .build();
        return userDao.save(user);
    }


    @Transactional
    @Override
    public UserEntity findById(Integer id) {
        return userDao.findById(id).orElse(null);
        
    }

    @Transactional
    @Override
    public void delete(UserEntity user) {
        userDao.delete(user);
    }

    @Transactional
    @Override
    public boolean existUsername(String username){
        if(userDao.findByUsername(username).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
         UserEntity user = userDao.findByUsername(username).orElseThrow(null);
        return user;
    }



}
