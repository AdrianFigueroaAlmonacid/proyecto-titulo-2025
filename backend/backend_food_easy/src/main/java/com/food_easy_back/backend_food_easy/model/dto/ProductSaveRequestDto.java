package com.food_easy_back.backend_food_easy.model.dto;


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
public class ProductSaveRequestDto {

    private String name;
    private double price;
    private Integer quantity;
    private Long category;
    private LocalDate expirationDate;
}
