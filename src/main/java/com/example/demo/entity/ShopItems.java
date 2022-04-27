package com.example.demo.entity;

import com.example.demo.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShopItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ItemsCategory itemsCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gender gender;

    @Column(name = "brand")
    private String brand;

    @Column(name = "img")
    private String img;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Size sizes;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;
}
