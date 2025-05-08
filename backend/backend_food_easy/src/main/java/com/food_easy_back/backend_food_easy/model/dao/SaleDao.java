package com.food_easy_back.backend_food_easy.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food_easy_back.backend_food_easy.model.dto.product.SaleListDto;
import com.food_easy_back.backend_food_easy.model.entity.SaleEntity;


@Repository
public interface SaleDao extends CrudRepository<SaleEntity, Long> {



    @Query("SELECT new com.food_easy_back.backend_food_easy.model.dto.product.SaleListDto(s.product.name,s.price, s.quantity, s.saleDate) " +
       "FROM SaleEntity s " +
       "WHERE FUNCTION('MONTH', s.saleDate) = :month " +
       "AND FUNCTION('YEAR', s.saleDate) = :year AND s.product.category.store.idStore = :idStore")
    List<SaleListDto> findSalesInMonth(@Param("month") int month, @Param("year") int year,@Param("idStore") Integer idStore);
}
