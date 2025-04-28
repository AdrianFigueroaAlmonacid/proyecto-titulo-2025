package com.food_easy_back.backend_food_easy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserListDto {
    private Integer IdUser;
    private String name;
    private String position;
    private boolean admin;
}
