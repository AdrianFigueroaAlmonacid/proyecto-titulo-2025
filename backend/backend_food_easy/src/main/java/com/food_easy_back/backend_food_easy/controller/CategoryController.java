package com.food_easy_back.backend_food_easy.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dto.category.CategoryDto;
import com.food_easy_back.backend_food_easy.model.dto.category.CategorySaveDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.ICategoryService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Endpoint para obtener la categoria por su id

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id ){
        try {
            CategoryEntity categoryEntity = categoryService.findById(id);
            CategoryDto categoryDto = CategoryDto.builder()
                                                .name(categoryEntity.getName())
                                                .id(categoryEntity.getIdCategory())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Categoria obtenida correctamente")
                                .object(categoryDto)
                                .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message(e.getMessage())
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al obtener categorias")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para obtener todas las categorias de un usuario del negocio

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories( ){
        try {
            List<CategoryEntity> categoryEntity = categoryService.getAllCategories();
            List<CategoryDto> categoryDto = categoryEntity.stream()
                                            .map(cat -> new CategoryDto(cat.getName(),cat.getIdCategory()))
                                            .collect(Collectors.toList());
            ResponseMessage response = ResponseMessage.builder()
                                .message("Categorias obtenidas correctamente")
                                .object(categoryDto)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

        }catch(ResponseStatusException e){
            ResponseMessage response = ResponseMessage.builder()
                                .message(e.getMessage())
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al obtener categorias")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    //Endpoint para guardar una categoria
    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategorySaveDto category){
       try {
        CategoryEntity categoryEntity = categoryService.saveCategory(category);

        CategoryDto categoryDto = CategoryDto.builder()
                                                .name(categoryEntity.getName())
                                                .id(categoryEntity.getIdCategory())
                                                .build();
        ResponseMessage response = ResponseMessage.builder()
                                .message("Categoria guardada correctamente")
                                .object(categoryDto)
                                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    
        }catch(ResponseStatusException e){
        ResponseMessage response = ResponseMessage.builder()
                            .message(e.getMessage())
                            .object(null)
                            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        
       } catch (Exception e) {
        ResponseMessage error = ResponseMessage.builder()
                                .message("Error al guardar categoria")
                                .object(null)
                                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

       }
    }
}
