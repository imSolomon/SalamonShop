package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//@Entity
//@Table(name = "order")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "items_id")
//    private ShopItems items;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private Users users;
//
//    @Column(name = "date")
//    @JsonFormat(pattern = "YYYY-MM-DD", shape = JsonFormat.Shape.STRING)
//    private LocalDate date;
//
//    private Long quantity;
//
//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "number")
//    private double number;
//}
