package com.food_easy_back.backend_food_easy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dto.product.ProductListDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSaveDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSellDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductUpdateDto;
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


    //Metodo get para obtener productos con su categoria
    @GetMapping("/products/{category}")
    public ResponseEntity<?> getProducts(@PathVariable String category,Pageable pageable){
        try {

            Page<ProductEntity> productsPage = productService.getProductsByCategory(category, pageable);
            Page<ProductListDto> dtoPage = productsPage.map(u -> ProductListDto.builder()
                                                                .category(u.getCategory().getName())
                                                                .name(u.getName())
                                                                .price(u.getPrice())
                                                                .id(u.getIdProduct())
                                                                .quantity(u.getQuantity())
                                                                .expirationDate(u.getExpirationDate())
                                                                .build());
            ResponseMessage response = ResponseMessage.builder()
                                .message("Productos recuperados correctamente")
                                .object(dtoPage)
                                .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ResponseMessage error = ResponseMessage.builder()
                                .message("Error al recuperar usuarios")
                                .object(null)
                                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    
    //Endpoint para guardar producto
    @PostMapping
    public ResponseEntity <?> saveProduct(@RequestBody ProductSaveDto productDto ){
        try{
            ProductEntity product = productService.saveProduct(productDto);
            ProductSaveDto productdto = ProductSaveDto.builder()
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
    public ResponseEntity <?> sellProduct(@RequestBody ProductSellDto productDto ){
        try{
            ProductEntity product = productService.sellProduct(productDto);
            ProductSaveDto productdto = ProductSaveDto.builder()
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .quantity(productDto.getQuantity())
                                                .build();
            ResponseMessage response = ResponseMessage.builder()
                                .message("Producto vendido correctamente por la cantidad de: " + productDto.getQuantity())
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
                                    .message("Error al vender producto: " + e.getMessage())
                                    .object(null)
                                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }

    }
    @PutMapping
    public ResponseEntity <?> updateProduct(@RequestBody ProductUpdateDto productDto ){
        try{
            ProductEntity product = productService.updateProduct(productDto);
            ProductSaveDto productdto = ProductSaveDto.builder()
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
