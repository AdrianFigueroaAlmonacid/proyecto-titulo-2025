package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.product.ProductSaveDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductSellDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductUpdateDto;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;

public interface IProductService {

    ProductEntity saveProduct (ProductSaveDto productDto);

    void deleteProduct(Long id);

    ProductEntity updateProduct (ProductUpdateDto productDto);

    ProductEntity sellProduct(ProductSellDto productDto);


}
