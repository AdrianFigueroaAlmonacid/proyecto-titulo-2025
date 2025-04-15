package com.food_easy_back.backend_food_easy.model.entity;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column(name= "name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="sell_price")
    private double sell_price;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name= "expiration_date")
    private LocalDate expirationDate;
    
    @ManyToOne
    private CategoryEntity category;

}
