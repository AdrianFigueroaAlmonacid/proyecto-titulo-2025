package com.food_easy_back.backend_food_easy.model.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;



@Repository
public interface CategoryDao extends CrudRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByStore(StoreEntity store);

}
