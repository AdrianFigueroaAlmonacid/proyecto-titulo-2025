package com.food_easy_back.backend_food_easy.model.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;


@Repository
public interface ProductDao extends CrudRepository <ProductEntity,Long> {

    Optional <ProductEntity> findByName(String name);

}
