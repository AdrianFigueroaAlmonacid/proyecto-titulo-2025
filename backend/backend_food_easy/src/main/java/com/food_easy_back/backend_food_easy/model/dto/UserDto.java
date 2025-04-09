package com.food_easy_back.backend_food_easy.model.dto;

import java.io.Serializable;

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
public class UserDto implements Serializable{

    private Integer IdUser;
    private String email;
    private String name;
    private String username;
    private String lastName;
    private String role;
    private Integer store;


}
