package com.food_easy_back.backend_food_easy.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food_easy_back.backend_food_easy.model.dto.OwnerCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateDto;
import com.food_easy_back.backend_food_easy.model.dto.UserPasswordDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;

public interface IUserService {

    UserEntity saveUserByAdmin(UserCreateDto userdto);

    UserEntity saveOwner(OwnerCreateDto requestDto);

    UserEntity updateUser(UserUpdateDto userdto);

    UserEntity updateUserPassword(UserPasswordDto userdto);
    
    UserEntity findById(Integer id);

    void delete( Integer id);

    boolean existUsername(String username);

    UserEntity findByUsername(String username);

    Page<UserEntity> getUsersByStore(Pageable pageable);

    String setPrivileges(List<UserRoleEntity> roles);

}
