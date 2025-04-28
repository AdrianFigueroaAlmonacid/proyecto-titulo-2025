package com.food_easy_back.backend_food_easy.model.dto.product;

import java.time.LocalDate;

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
public class ProductExpiringDto {

    private String name;
    private LocalDate date;
}
