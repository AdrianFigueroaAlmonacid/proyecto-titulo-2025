package com.food_easy_back.backend_food_easy.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long idCategory;

    @Column(name = "name")
    private String name;



    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

}
