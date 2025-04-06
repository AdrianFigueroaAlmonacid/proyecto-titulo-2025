package com.food_easy_back.backend_food_easy.model.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.food_easy_back.backend_food_easy.model.entity.UserEntity;

public interface UserDao extends CrudRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);

}
