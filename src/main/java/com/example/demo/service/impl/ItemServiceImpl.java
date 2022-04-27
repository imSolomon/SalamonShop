package com.example.demo.service.impl;

import com.example.demo.entity.ShopItems;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ShopItems addItem(ShopItems item) {
        return itemRepository.save(item);
    }

    @Override
    public List<ShopItems> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public ShopItems getItem(Long id) {

        return itemRepository.findById(id).get();
    }

    @Override
    public void deleteItem(ShopItems item) {
        itemRepository.delete(item);
    }

    @Override
    public ShopItems saveItems(ShopItems item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItemByID(Long id){
        itemRepository.deleteById(id);
    }

    @Override
    public List<ShopItems> findAllByItemsCategory_IdAndGender_Id(Long categoryId, Long genderId) {
        return itemRepository.findAllByItemsCategory_IdAndGender_Id(categoryId, genderId);
    }

    @Override
    public Page<ShopItems> listAllByItemsCategory_IdAndGender_Id(Integer pageNum, String sortField, String sortDirection, Long categoryId, Long genderId) {
        Pageable pageable = PageRequest.of(pageNum-1, 3,
                sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        itemRepository.findAllByItemsCategory_IdAndGender_Id(categoryId, genderId);
        return itemRepository.findAll(pageable);
    }

    @Override
    public Page<ShopItems> findPaginated(Integer pageNum,String sortField, String sortDirection, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, 3, sortDirection.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
        if(name!=null){
            return itemRepository.searchByNameLike(name,pageable);
        }
        return itemRepository.findAll(pageable);
    }

    @Override
    public List<ShopItems> searchByPriceLike(int min, int max) {
        return itemRepository.searchByPriceLike(min,max);
    }


//    @Override
//    public Page<ShopItems> findPage(int page, int size) {
//        Pageable pageable = PageRequest.of(page - 1,size);
//        return this.itemRepository.findAll(pageable);
//    }

//    @Override
//    public List<ShopItems> findByCategoryAndGender(ItemsCategory itemsCategory, Gender gender){
//        return itemRepository.findByCategoryAndGender(itemsCategory,gender);
//    }
//
//    @Override
//    public List<ShopItems> findByGender(Gender gender) {
//        return itemRepository.findByGender(gender);
//    }
//
//    @Override
//    public List<ShopItems> findByBrand(String brand){
//        return itemRepository.findByBrand(brand);
//    }
}
