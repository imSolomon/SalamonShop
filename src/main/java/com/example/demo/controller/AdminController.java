//package com.example.demo.controller;
//
//import com.example.demo.entity.ShopItems;
//import com.example.demo.service.ItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private ItemService itemService;
//
//    @GetMapping("/admin")
//    public String addService(Model model){
//        List<ShopItems> itemList = itemService.getAllItems();
//        if (itemList!=null){
//            model.addAttribute("tovary", itemList);
//        }
//
//        return "admin";
//    }
//
//    @GetMapping(value = "/details/{id}")
//    public String details(@PathVariable(name = "id")Long id, Model model){
//        ShopItems items = itemService.getItem(id);
//        model.addAttribute("item",items);
//        return "details";
//    }
//}
