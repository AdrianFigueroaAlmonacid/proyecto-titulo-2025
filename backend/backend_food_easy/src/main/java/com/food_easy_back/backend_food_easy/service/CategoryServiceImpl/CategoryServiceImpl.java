package com.food_easy_back.backend_food_easy.service.CategoryServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.CategoryDao;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.service.ICategoryService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private CategoryDao categoryDao;

    
    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }



    @Override
    public CategoryEntity findById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
    }

}
