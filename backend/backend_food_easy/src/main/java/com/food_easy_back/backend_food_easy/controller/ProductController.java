package com.food_easy_back.backend_food_easy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dto.ProductSaveRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.ProductUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;
import com.food_easy_back.backend_food_easy.model.payload.ResponseMessage;
import com.food_easy_back.backend_food_easy.service.IProductService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    
    @PostMapping
    public ResponseEntity <?> saveProduct(@RequestBody ProductSaveRequestDto productDto ){
        try{
            ProductEntity product = productService.saveProduct(productDto);
            ProductSaveRequestDto productdto = ProductSaveRequestDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto guardado correctamente")
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @PostMapping("/sell")
    public ResponseEntity <?> saveProduct(@RequestBody ProductSaveRequestDto productDto ){
        try{
            ProductEntity product = productService.saveProduct(productDto);
            ProductSaveRequestDto productdto = ProductSaveRequestDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto guardado correctamente")
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al crear producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }
    @PutMapping
    public ResponseEntity <?> updateProduct(@RequestBody ProductUpdateRequestDto productDto ){
        try{
            ProductEntity product = productService.updateProduct(productDto);
            ProductSaveRequestDto productdto = ProductSaveRequestDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto actualizado correctamente")
                                .object(productdto)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(ResponseStatusException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getReason())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }catch(Exception e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al actualizar producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }

    @DeleteMapping
    public ResponseEntity <?>  deleteProduct (Long id){
        try {
            productService.deleteProduct(id);
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto eliminado correctamente")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }catch(EntityNotFoundException e){
            ResponseMessage error = ResponseMessage.builder()
                                    .message(e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                    .message("Error al eliminar producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
