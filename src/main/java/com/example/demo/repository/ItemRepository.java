package com.example.demo.repository;


import com.example.demo.entity.ShopItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<ShopItems, Long> {
    List<ShopItems> findAllByItemsCategory_IdAndGender_Id(Long categoryId, Long genderId);

    List<ShopItems> findAllByNameLike(String name);

    @Query("SELECT m FROM ShopItems m WHERE CONCAT(m.name,' ') like %?1%")
    Page<ShopItems> searchByNameLike(String name, Pageable pageable);

    @Query("FROM ShopItems WHERE price BETWEEN :min and :max")
    List<ShopItems> searchByPriceLike(@Param("min") int min, @Param("max") int max);

//    Page<ShopItems> findAll(Pageable pageable);


//    List<ShopItems> findByGender(Gender gender);
//    List<ShopItems> findByBrand(String brand);

}
