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
public class UserUpdateDto {

    private Integer id;
    private String email;
    private String name;
    private String username;
    private String lastName;

}
