package com.food_easy_back.backend_food_easy.service.CategoryServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.food_easy_back.backend_food_easy.model.dao.CategoryDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.category.CategorySaveDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.ICategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private CategoryDao categoryDao;
    private UserDao userDao;

    
    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, UserDao userDao) {
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }




    @Override
    public CategoryEntity findById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
    }


    @Transactional
    @Override
    public CategoryEntity saveCategory(CategorySaveDto category) {

        UserEntity user = findByUsername(getCurrentUsername());
        StoreEntity store = user.getStore();
        CategoryEntity categoryEntity = CategoryEntity.builder()
                                                        .name(category.getName())
                                                        .store(store)
                                                        .build();
        return categoryDao.save(categoryEntity);
    }

    //Metodo que devuelve el usuario actual de la peticion
    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    public UserEntity findByUsername(String username) {
         UserEntity user = userDao.findByUsername(username).orElseThrow(null);
        return user;
    }

}
