package com.food_easy_back.backend_food_easy.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "store")
public class StoreEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStore;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "store",cascade = CascadeType.REMOVE)
    private List<UserEntity> employees;

    
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<CategoryEntity> categories;

}
