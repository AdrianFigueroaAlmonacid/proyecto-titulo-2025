package com.food_easy_back.backend_food_easy.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food_easy_back.backend_food_easy.model.dto.OwnerCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserRoleEntity;

public interface IUserService {

    UserEntity saveUserByAdmin(UserCreateRequestDto userdto);

    UserEntity saveOwner(OwnerCreateRequestDto requestDto);

    UserEntity updateUser(UserUpdateRequestDto userdto);
    
    UserEntity findById(Integer id);

    void delete( Integer id);

    boolean existUsername(String username);

    UserEntity findByUsername(String username);

    Page<UserEntity> getUsersByStore(Pageable pageable);

    String setPosition(List<UserRoleEntity> roles);

}
