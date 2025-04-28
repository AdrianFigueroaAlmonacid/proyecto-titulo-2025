package com.food_easy_back.backend_food_easy.service.CategoryServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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




    //Metodo para encontrar una categoría por su id
    @Override
    public CategoryEntity findById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
    }



    //Metodo que guarda la categoria de acuerdo al negocio del usuario
    @Transactional
    @Override
    public CategoryEntity saveCategory(CategorySaveDto category) {

        UserEntity user = findByUsername(getCurrentUsername());
        if(user==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El usuario no esta autenticado");
        }
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
    //Metodo que devuelve el userEntoty con el username
    public UserEntity findByUsername(String username) {
         UserEntity user = userDao.findByUsername(username).orElseThrow(null);
        return user;
    }




    @Override
    public List<CategoryEntity> getAllCategories() {
        UserEntity user = findByUsername(getCurrentUsername());
        if(user==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El usuario no esta autenticado");
        }
        StoreEntity store = user.getStore();
        return categoryDao.findAllByStore(store);
    }

}
