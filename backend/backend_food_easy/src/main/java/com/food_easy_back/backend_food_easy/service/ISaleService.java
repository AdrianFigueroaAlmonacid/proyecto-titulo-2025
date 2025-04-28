package com.food_easy_back.backend_food_easy.service;
import java.util.List;

import com.food_easy_back.backend_food_easy.model.dto.product.SaleListDto;
import com.food_easy_back.backend_food_easy.model.entity.SaleEntity;
import com.food_easy_back.backend_food_easy.model.entity.UserEntity;

public interface ISaleService {

    SaleEntity saveSale(SaleEntity sale);

    List<SaleListDto> findSalesInMonth (int month, int year);

    UserEntity findByUsername(String username);

}
