package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.ProductSaveRequestDto;
import com.food_easy_back.backend_food_easy.model.dto.ProductUpdateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;

public interface IProductService {

    ProductEntity saveProduct (ProductSaveRequestDto productDto);

    void deleteProduct(Long id);

    ProductEntity updateProduct (ProductUpdateRequestDto productDto);


}
