package com.food_easy_back.backend_food_easy.model.entity;


import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name= "name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="sale_price")
    private double salePrice;

    @Column(name="purchase_price")
    private double purchasePrice;

    @Column(name= "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne
    private CategoryEntity category;
}
