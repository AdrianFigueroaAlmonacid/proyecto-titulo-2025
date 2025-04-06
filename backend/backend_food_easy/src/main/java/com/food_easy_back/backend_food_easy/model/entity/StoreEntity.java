package com.food_easy_back.backend_food_easy.model.entity;

import java.util.List;
import java.util.Locale.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity
public class StoreEntity {

    @Id 
    @GeneratedValue
    private Long idStore;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private UserEntity admin;


    @OneToMany(mappedBy = "store",cascade = CascadeType.REMOVE)
    private List<UserEntity> employees;

    
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Category> categories;

}
