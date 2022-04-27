package com.example.demo.service;

import com.example.demo.entity.ShopItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    ShopItems addItem(ShopItems item);
    List<ShopItems> getAllItems();
    ShopItems getItem(Long id);
    void deleteItem(ShopItems item);
    ShopItems saveItems(ShopItems items);
    void deleteItemByID(Long id);
    List<ShopItems> findAllByItemsCategory_IdAndGender_Id(Long categoryId, Long genderId);

    Page<ShopItems> listAllByItemsCategory_IdAndGender_Id(Integer pageable, String sortField, String sortDirection, Long categoryId, Long genderId);

    Page<ShopItems> findPaginated(Integer pageable, String sortField, String sortDirection, String name);

    List<ShopItems> searchByPriceLike(int min,int max);
//    Page<ShopItems> listSearchByPriceLike(Integer min, Integer max, String sortField, String sortDirection, Integer pageable);

//    List<ShopItems> findByGender(Gender gender);
//
//    List<ShopItems> findByBrand(String brand);
}
