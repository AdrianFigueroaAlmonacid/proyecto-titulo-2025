package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.StoreCreateDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;

public interface IStoreService {
    StoreEntity saveStore(StoreCreateDto storedto);
    
    StoreEntity findById(Integer id);

    void delete();

    boolean existStore(String namee);

    StoreEntity findByName(String name);
}
