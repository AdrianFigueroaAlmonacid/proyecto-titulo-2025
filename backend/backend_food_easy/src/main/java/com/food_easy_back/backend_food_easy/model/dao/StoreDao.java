package com.food_easy_back.backend_food_easy.model.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;


@Repository
public interface StoreDao extends CrudRepository<StoreEntity, Integer>{
    Optional<StoreEntity> findByName(String name);
}
