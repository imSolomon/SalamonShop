package com.example.demo.service.impl;

import com.example.demo.entity.ItemsCategory;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public void createCategory(ItemsCategory itemsCategory) {
         categoryRepository.save(itemsCategory);
    }

    @Override
    public List<ItemsCategory> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @Override
    public ItemsCategory saveCategory(ItemsCategory itemsCategory) {
        return categoryRepository.save(itemsCategory);
    }

    @Override
    public void deleteCategory(ItemsCategory itemsCategory) {
        categoryRepository.delete(itemsCategory);
    }

}
