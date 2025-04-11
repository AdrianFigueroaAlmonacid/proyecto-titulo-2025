package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;

public interface ICategoryService {

    CategoryEntity findById(Long id);

}
