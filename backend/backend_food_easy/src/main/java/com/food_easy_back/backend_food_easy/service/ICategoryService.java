package com.food_easy_back.backend_food_easy.service;

import java.util.List;

import com.food_easy_back.backend_food_easy.model.dto.category.CategorySaveDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;


public interface ICategoryService {

    CategoryEntity findById(Long id);

    CategoryEntity saveCategory(CategorySaveDto category);

    List<CategoryEntity> getAllCategories ();

}
