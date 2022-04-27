package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private final ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/main1")
    public String main(
//            @RequestParam(value = "category", required = false)ItemsCategory itemsCategory,
//                       @RequestParam(value = "gender", required = false)Gender gender,
//                       @RequestParam(value = "brand", required = false)String brand,
                        Model model) {

        model.addAttribute("currentUser",getUserData());
        String name = null;

//        if(!isBlank(String.valueOf(itemsCategory)) && !isBlank(String.valueOf(gender))){
//            List<ShopItems> itemsList = itemService.findByCategoryAndGender(itemsCategory,gender);
//            model.addAttribute("shopList",itemsList);
//        }else if(!isBlank(String.valueOf(gender))){
//            List<ShopItems> itemsList = itemService.findByGender(gender);
//            model.addAttribute("shopList", itemsList);
//        }else if(!isBlank(brand)){
//            List<ShopItems> itemsList = itemService.findByBrand(brand);
//            model.addAttribute("shopList", itemsList); СТАРЫЙ ВЫВОД ПО КАТЕГОРИИ И ПОЛ
        return findPaginated(1,model,"id","asc",name);
    }

    @GetMapping(value = "/price")
    public String price(@RequestParam(name = "min",defaultValue = "0",required = false) int min,
                        @RequestParam(name = "max",defaultValue = "9999999", required = false) int max,
                        Model model){
        model.addAttribute("currentUser",getUserData());

        model.addAttribute("itemsCategory",categoryService.getAllCategorys());
        model.addAttribute("genders", genderService.getAllGender());
        model.addAttribute("shopList", itemService.searchByPriceLike(min,max));

        model.addAttribute("min",min);
        model.addAttribute("max",max);

        return "index";
    }

    @GetMapping(value = "/page/{pageNum}")
    public String findPaginated(@PathVariable(value = "pageNum") Integer currentPage, Model model,
                                @Param("sortField") String sortField,
                                @Param("sortDirection") String sortDirection,
                                @Param("name") String name){
        model.addAttribute("currentUser", getUserData());

        Page<ShopItems> page1 = itemService.findPaginated(currentPage,sortField,sortDirection,name);

        List<ShopItems> shopItemsList = page1.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages",page1.getTotalPages());
        model.addAttribute("totalItems",page1.getTotalElements());
        model.addAttribute("items", shopItemsList);

        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDirection",sortDirection);
        model.addAttribute("reverseSortDirection",sortDirection.equals("asc")?"desc":"asc");
        model.addAttribute("name",name);
        model.addAttribute("itemsCategory",categoryService.getAllCategorys());
        model.addAttribute("genders", genderService.getAllGender());
        return "page";
    }

//    @GetMapping(value = "/search")
//    public String name(@RequestParam (name = "name") String name, Model model) {
//        model.addAttribute("currentUser",getUserData());
//
//        model.addAttribute("itemsCategory",categoryService.getAllCategorys());
//        model.addAttribute("genders", genderService.getAllGender());
//        model.addAttribute( "shopList",itemRepository.searchByNameLike(name));
//        return "index";
//    } HERE IS OLD METHOD OF SEARCHING

    @GetMapping("/sort-by-category/{categoryId}/gender/{genderId}")
    public String sortByCategory(@PathVariable(name = "categoryId") Long categoryId,
                                 @PathVariable(name = "genderId") Long genderId,
                                 Model model) {

        model.addAttribute("currentUser",getUserData());
        model.addAttribute("shopList",itemService.findAllByItemsCategory_IdAndGender_Id(categoryId,genderId));
        model.addAttribute("itemsCategory",categoryService.getAllCategorys());
        model.addAttribute("genders", genderService.getAllGender());
        return "index";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addService(Model model){
        model.addAttribute("currentUser", getUserData());
        List<ShopItems> itemList = itemService.getAllItems();
        model.addAttribute("allGenders", genderService.getAllGender());
        model.addAttribute("allCategory", categoryService.getAllCategorys());
        List<Size> sizeList = sizeService.getAllSize();
        model.addAttribute("allSize",sizeList);
        if (itemList!=null){
            model.addAttribute("tovary", itemList);
        }
        return "admin";
    }

    @PostMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItem(@RequestParam(name = "item-category", defaultValue = "NO category") ItemsCategory itemsCategory,
                          @RequestParam(name = "item-brand", defaultValue = "No brand") String brand,
                          @RequestParam(name = "item-gender",defaultValue = "No gender") Gender gender,
                          @RequestParam(name = "item-img", defaultValue = "No URL")String img,
                          @RequestParam(name = "item-name", defaultValue = "No name")String name,
                          @RequestParam(name = "item-size", defaultValue = "NO size")Size size,
                          @RequestParam(name = "item-price", defaultValue = "NO 0")int price,
                          @RequestParam(name = "item-quantity", defaultValue = "NO 0")int quantity,
                          Model model){

        ShopItems items = new ShopItems(null,itemsCategory,gender,brand,img,name,size,price,quantity);
        itemService.addItem(items);
        return "redirect:/admin";
    }

    @GetMapping("/addToBasket")
    public void basket(@RequestParam("id") Long id, Model model, HttpServletRequest request,
                         HttpServletResponse response){
        model.addAttribute("currentUser",getUserData());
        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");

        if(items==null){
            items =  new HashMap<>();
            request.getSession().setAttribute("item",items);

        }

        ShopItems items1 = itemService.getItem(id);

        if(items.containsKey(id)){
            ArrayList<ShopItems> items2 = items.get(id);
            items2.add(items1);
            items.replace(items1.getId(),items2);
        }else{
            ArrayList<ShopItems> list = new ArrayList<>();
            list.add(items1);
            items.put(items1.getId(),list);
        }

        request.getSession().setAttribute("item",items);
//        ShopItems items = itemService.getItem(id);
//        HttpSession session = request.getSession();
//        session.setAttribute("item", items);
//        model.addAttribute(String.valueOf(items));

    }

    @PatchMapping("/refresh/{id}")
    public String refresh(@PathVariable("id") Long id,
            Model model,HttpServletRequest request){
        model.addAttribute("currentUser",getUserData());

        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");

        if(items!=null){
            request.getSession().setAttribute("item",items);
            ShopItems items1 = itemService.getItem(id);

            if(items.containsKey(id)){
                ArrayList<ShopItems> items2 = items.get(id);
                items2.add(items1);
                items.replace(items1.getId(),items2);
            }
        }
        return "redirect:/basket";
    }

    @PostMapping("deleteItem/{id}")
    public String deletes(@PathVariable("id")Long id, HttpServletRequest request){
        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");
        items.remove(id);
        return "redirect:/basket";
    }

    @PostMapping("order/{id}")
    public String buyItem(@PathVariable("id") Long id, HttpServletRequest request){
        Map<Long, ArrayList<ShopItems>> items = (Map<Long, ArrayList<ShopItems>>) request.getSession().getAttribute("item");
        items.get(id);
        return "order";
    }

    @GetMapping(value = "/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String details(@PathVariable(name = "id")Long id,Model model){
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("item",itemService.getItem(id));
        model.addAttribute("allCategory",categoryService.getAllCategorys());
        model.addAttribute("allSize",sizeService.getAllSize());
        model.addAttribute("allGender",genderService.getAllGender());

        return "details";
    }

    @PostMapping(value = "/saveItems")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveItem(@RequestParam(name = "item-id", defaultValue = "No id")Long id,
                           @RequestParam(name = "item-category", defaultValue = "NO category")ItemsCategory itemsCategory,
                           @RequestParam(name = "item-brand", defaultValue = "No brand") String brand,
                           @RequestParam(name = "item-gender",defaultValue = "No gender")Gender gender,
                           @RequestParam(name = "item-img", defaultValue = "No URL")String img,
                           @RequestParam(name = "item-name", defaultValue = "No name")String name,
                           @RequestParam(name = "item-size", defaultValue = "")Size size,
                           @RequestParam(name = "item-price", defaultValue = "NO 0")int price){
        ShopItems items = itemService.getItem(id);

        if(items !=null){
            items.setItemsCategory(itemsCategory);
            items.setBrand(brand);
            items.setGender(gender);
            items.setImg(img);
            items.setSizes(size);
            items.setName(name);
            items.setPrice(price);
            itemService.saveItems(items);

        }
        return "redirect:/admin";
    }

//    @PostMapping(value = "/addShoes")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
//    public String addShoes(@RequestParam(name = "item-category", defaultValue = "NO category")ItemsCategory itemsCategory,
//                          @RequestParam(name = "item-brand", defaultValue = "No brand") String brand,
//                          @RequestParam(name = "item-gender",defaultValue = "No gender")Gender gender,
//                          @RequestParam(name = "item-img", defaultValue = "No URL")String img,
//                          @RequestParam(name = "item-name", defaultValue = "No name")String name,
//                          @RequestParam(name = "item-size", defaultValue = "NO size") Size size,
//                          @RequestParam(name = "item-price", defaultValue = "NO 0")String price,
//                          Model model){
//
//        ShopItems items = new ShopItems(null,itemsCategory,gender,brand,img,name,size,price);
//        itemService.addItem(items);
//        return "redirect:/admin";
//    } Here is add shoes

    @GetMapping(value = "/deleteItems/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteItem(@PathVariable(name = "id")Long id){
        itemService.deleteItemByID(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "register")
    public String toRegister(Model model){
        model.addAttribute("currentUser",getUserData());
        return "register";
    }

    @GetMapping(value = "403")
    public String accessDenied(Model model){

        model.addAttribute("currentUser",getUserData());
        return "403";
    }

    @GetMapping("/sign")
    public String sign(Model model){
        model.addAttribute("currentUser",getUserData());
        return "sign";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser",getUserData());
        return "profile";
    }

    @PostMapping(value = "/register")
    public String toRegister(@RequestParam(name = "user_FullName")String fullName,
                             @RequestParam(name = "user_PhoneNum")String phoneNum,
                             @RequestParam(name = "user_name")String mail,
                             @RequestParam(name = "user_password")String password,
                             @RequestParam(name = "user_RealPassword")String realPassword){
        if(password.equals(realPassword)){
            Users newUser = new Users();
            newUser.setFullName(fullName);
            newUser.setPhoneNum(phoneNum);
            newUser.setMail(mail);
            newUser.setPassword(password);
            if (userService.createUser(newUser)!=null) {
                return "redirect:/register?success";
            }
        }
        return "redirect:/register?ERROR";
    }

    @GetMapping(value = "/basket")
    public String basket(Model model){
        model.addAttribute("currentUser", getUserData());
        return "basket";
    }

    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User) authentication.getPrincipal();
            Users myUsers = userService.getUserByEmail(secUser.getUsername());
            return myUsers;
        }
        return null;
    }

    @GetMapping(value = "/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String Category123(Model model){
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("Categories",categoryService.getAllCategorys());
        return "category";
    }

    @PostMapping(value = "/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addCategory(Model model,@RequestParam(name = "item-category")String itemCategory){
        model.addAttribute("currentUser", getUserData());
        ItemsCategory category = new ItemsCategory(null,itemCategory);
        categoryService.createCategory(category);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteCategory/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteCategory(@PathVariable(name = "id") ItemsCategory id){
        categoryService.deleteCategory(id);
        return "redirect:/addCategory";
    }

    @GetMapping(value = "/contacts")
    public String contact(Model model){
        model.addAttribute("currentUser", getUserData());
        return "contacts";
    }

}