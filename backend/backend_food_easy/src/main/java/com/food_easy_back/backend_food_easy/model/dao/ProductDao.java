package com.food_easy_back.backend_food_easy.model.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;


@Repository
public interface ProductDao extends CrudRepository <ProductEntity,Long> {

    Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable page);

}
