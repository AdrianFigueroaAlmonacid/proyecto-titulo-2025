package com.food_easy_back.backend_food_easy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.food_easy_back.backend_food_easy.model.dto.product.ProductExpiringDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductLowDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSaveDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSellDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;

public interface IProductService {

    ProductEntity saveProduct (ProductSaveDto productDto);

    void deleteProduct(Long id);

    ProductEntity updateProduct (ProductUpdateDto productDto);

    ProductEntity sellProduct(ProductSellDto productDto);

    Page<ProductEntity> getProductsByCategory(String category,Pageable pageable);

    Page<ProductEntity> getProducts(Pageable pageable);

    Integer showCountLowProducts();

    Integer countExpiringSoon();

    List<ProductLowDto> showLowProducts();

    List<ProductExpiringDto> showExpiringProducts();

    List<ProductExpiringDto> showExpiredProducts();


}
