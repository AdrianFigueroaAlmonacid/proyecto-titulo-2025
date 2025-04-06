package com.food_easy_back.backend_food_easy.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Entity
public class ProductEntity {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProduct;

    @Column(name= "name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="sale_price")
    private double salePrice;

    @Column(name="purchase_price")
    private double purchasePrice;

    @ManyToOne
    private CategoryEntity category;
}
