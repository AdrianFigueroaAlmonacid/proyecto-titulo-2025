package com.food_easy_back.backend_food_easy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_easy_back.backend_food_easy.model.dto.category.CategorySaveDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.ICategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategorySaveDto category){
       try {
        CategoryEntity categoryEntity = categoryService.saveCategory(category);
        CategorySaveDto categoryDto = CategorySaveDto.builder()
                                                        .name(categoryEntity.getName())
                                                        .build();
        ResponseMessage response = ResponseMessage.builder()
                                .message("Categoria recuperada correctamente")
                                .object(categoryDto)
                                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        
       } catch (Exception e) {
        ResponseMessage error = ResponseMessage.builder()
                                .message("Error al guardar categoria")
                                .object(null)
                                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

       }
    }
}
