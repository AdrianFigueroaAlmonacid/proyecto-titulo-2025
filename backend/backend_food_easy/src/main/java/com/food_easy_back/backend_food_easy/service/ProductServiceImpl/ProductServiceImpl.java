package com.food_easy_back.backend_food_easy.service.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food_easy_back.backend_food_easy.model.dao.ProductDao;
import com.food_easy_back.backend_food_easy.model.dao.UserDao;
import com.food_easy_back.backend_food_easy.model.dto.ProductSaveRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.ProductUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;
import com.food_easy_back.backend_food_easy.service.ICategoryService;
import com.food_easy_back.backend_food_easy.service.IProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class ProductServiceImpl implements IProductService {

    private ProductDao productDao ;
    private ICategoryService categoryService;
    private UserDao userDao;

    
    @Autowired
    public ProductServiceImpl(ProductDao productDao, ICategoryService categoryService, UserDao userDao) {
        this.productDao = productDao;
        this.categoryService = categoryService;
        this.userDao = userDao;
    }


    

    @Transactional
    @Override
    public ProductEntity saveProduct(ProductSaveRequestDto productDto) {



        CategoryEntity category = categoryService.findById(productDto.getCategory());


        ProductEntity product = ProductEntity.builder()
                                            .name(productDto.getName())
                                            .price(productDto.getPrice())
                                            .quantity(productDto.getQuantity())
                                            .category(category)
                                            .expirationDate(productDto.getExpirationDate())
                                            .build();
        return productDao.save(product);
    }

    @Transactional
    @Override
    public ProductEntity updateProduct(ProductUpdateRequestDto productDto) {



        CategoryEntity category = categoryService.findById(productDto.getCategory());


        ProductEntity product = ProductEntity.builder()
                                            .idProduct(productDto.getId())
                                            .name(productDto.getName())
                                            .price(productDto.getPrice())
                                            .quantity(productDto.getQuantity())
                                            .category(category)
                                            .build();
        return productDao.save(product);
    }



    @Override
    public void deleteProduct(Long id) {
        
        ProductEntity product = productDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

        CategoryEntity category = product.getCategory();

        UserEntity user = userDao.findByUsername(getCurrentUsername()).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado para hacer la acción."));

        if(category.getStore().equals(user.getStore())){
            productDao.deleteById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo puedes eliminar productos de tu negocio." );
        }
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
