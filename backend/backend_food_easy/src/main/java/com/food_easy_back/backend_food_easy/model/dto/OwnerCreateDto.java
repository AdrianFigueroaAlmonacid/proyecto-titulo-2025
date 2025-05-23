package com.food_easy_back.backend_food_easy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OwnerCreateDto {
    private UserCreateDto user;
    private StoreCreateDto store;
}
