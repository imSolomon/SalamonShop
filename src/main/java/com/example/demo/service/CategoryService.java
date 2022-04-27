package com.example.demo.service;

import com.example.demo.entity.ItemsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryService{
    void createCategory(ItemsCategory itemsCategory);
    List<ItemsCategory> getAllCategorys();
    ItemsCategory saveCategory(ItemsCategory itemsCategory);
    void deleteCategory(ItemsCategory itemsCategory);


}
