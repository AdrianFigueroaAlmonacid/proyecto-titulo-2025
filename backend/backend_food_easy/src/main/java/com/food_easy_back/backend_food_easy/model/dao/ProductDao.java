package com.food_easy_back.backend_food_easy.model.dao;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.dto.product.ProductExpiringDto;
import com.food_easy_back.backend_food_easy.model.dto.product.ProductLowDto;
import com.food_easy_back.backend_food_easy.model.entity.CategoryEntity;
import com.food_easy_back.backend_food_easy.model.entity.ProductEntity;


@Repository
public interface ProductDao extends CrudRepository <ProductEntity,Long> {

    Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable page);

    @Query("SELECT COUNT(p) FROM ProductEntity p " +
       "WHERE p.quantity <= 5 AND p.category.store.idStore= :idStore")
    Integer showCountLowProducts(@Param("idStore") Integer idStore);

    @Query("SELECT COUNT(p) FROM ProductEntity p " +
        "WHERE p.expirationDate <= :limitDate AND p.category.store.idStore = :idStore")
    Integer countExpiringSoon(@Param("limitDate") LocalDate limitDate, @Param("idStore") Integer idStore);

    @Query("SELECT new com.food_easy_back.backend_food_easy.model.dto.product.ProductLowDto(p.name, p.quantity) FROM ProductEntity p " +
       "WHERE p.quantity <= 5 AND p.category.store.idStore= :idStore")
    List<ProductLowDto> showLowProducts(@Param("idStore") Integer idStore);

    @Query("SELECT new com.food_easy_back.backend_food_easy.model.dto.product.ProductExpiringDto(p.name, p.expirationDate)  FROM ProductEntity p " +
        "WHERE p.expirationDate <= :limitDate AND p.category.store.idStore = :idStore")
    List<ProductExpiringDto> ExpiringSoon(@Param("limitDate") LocalDate limitDate, @Param("idStore") Integer idStore);




}
