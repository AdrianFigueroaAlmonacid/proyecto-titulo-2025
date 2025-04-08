package com.food_easy_back.backend_food_easy.model.dto;

import java.util.List;


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
public class UserCreateRequestDto {

    private String password;
    private String email;
    private String name;
    private String username;
    private String lastname;
    private List<String> roles;
    private Integer store;

}
