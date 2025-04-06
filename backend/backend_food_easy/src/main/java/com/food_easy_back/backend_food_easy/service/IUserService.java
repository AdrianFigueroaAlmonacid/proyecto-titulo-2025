package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.UserDto;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;

public interface IUserService {

    UserEntity save(UserDto userdto);
    
    UserEntity findById(Integer id);

    void delete( UserEntity user);

    boolean existUsername(String username);

    UserEntity findByUsername(String username);

}
