package com.food_easy_back.backend_food_easy.model.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;

@Repository
public interface UserDao extends CrudRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);

    Page<UserEntity> findAllByStore(StoreEntity store, Pageable pageable);

}
