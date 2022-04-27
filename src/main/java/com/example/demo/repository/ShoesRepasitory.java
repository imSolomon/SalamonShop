package com.example.demo.repository;

import com.example.demo.entity.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepasitory extends JpaRepository <ShopItems, Long> {
}
