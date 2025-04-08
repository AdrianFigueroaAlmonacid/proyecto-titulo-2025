package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.UserCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.UserUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;

public interface IUserService {

    UserEntity saveUser(UserCreateRequestDto userdto);

    UserEntity updateUser(UserUpdateRequestDto userdto);
    
    UserEntity findById(Integer id);

    void delete( UserEntity user);

    boolean existUsername(String username);

    UserEntity findByUsername(String username);

}
