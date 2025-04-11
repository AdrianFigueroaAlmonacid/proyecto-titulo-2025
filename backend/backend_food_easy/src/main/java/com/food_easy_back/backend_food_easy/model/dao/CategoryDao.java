package com.food_easy_back.backend_food_easy.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;


@Repository
public interface CategoryDao extends CrudRepository<CategoryEntity, Long> {

}
