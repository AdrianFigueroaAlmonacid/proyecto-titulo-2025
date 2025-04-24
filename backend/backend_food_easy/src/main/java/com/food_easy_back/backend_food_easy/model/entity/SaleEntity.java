package com.food_easy_back.backend_food_easy.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "sale")
public class SaleEntity {

    @Id
    @Column(name = "id_sale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSale;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "sale_date", updatable = false)
    private LocalDate saleDate;

    @ManyToOne
    private ProductEntity product;

    @PrePersist
    protected void onCreate() {
        saleDate = LocalDate.now();
    }

}
