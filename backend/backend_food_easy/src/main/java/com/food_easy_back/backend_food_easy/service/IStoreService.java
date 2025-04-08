package com.food_easy_back.backend_food_easy.service;

import com.food_easy_back.backend_food_easy.model.dto.StoreCreateRequestDto;
import com.food_easy_back.backend_food_easy.model.entity.StoreEntity;

public interface IStoreService {
    StoreEntity saveStore(StoreCreateRequestDto storedto);
    
    StoreEntity findById(Integer id);

    void delete( StoreEntity Store);

    boolean existStore(String namee);

    StoreEntity findByName(String name);
}
