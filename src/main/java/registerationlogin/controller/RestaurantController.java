package registerationlogin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;
import registerationlogin.entity.Category;
import registerationlogin.service.CategoryService;
import registerationlogin.service.MenuItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private MenuItemService menuItemService;
    private CategoryService categoryService;

    public RestaurantController(MenuItemService menuItemService, CategoryService categoryService) {
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
    }


   //handler methods for restaurant dashboard.
    @GetMapping("/dashboard")
    public String restaurant(){
        return "restaurant";
    }
    
    @GetMapping("/menu")
        public String menu(Model model) {
        List<ResMenuItemDto> menuItems = menuItemService.findAllItems();
        // System.out.println("menuItems: " + menuItems);
        model.addAttribute("menuItems", menuItems);
        return "menu";
    }

   @GetMapping("/add-menu")
        public String showMenuItemForm(Model model) {
        ReqMenuItemDto menuItemDto = new ReqMenuItemDto();
        model.addAttribute("menuItem", menuItemDto);
        model.addAttribute("categories", categoryService.findAll());
        return "form";
    }

    @PostMapping("/save")
    public String saveMenuItem(@Valid @ModelAttribute("menuItem") ReqMenuItemDto menuItemDto, BindingResult result, Model model ,@RequestParam("image") MultipartFile image,@RequestParam(value = "newCategory", required = false) String newCategory) {

         // Check if a new category is provided
    if (newCategory != null && !newCategory.trim().isEmpty()) {

        try{
        // Save the new category if it doesn't already exist
        Category category = new Category();
        category.setName(newCategory);
        categoryService.save(category);
        
        // Set the new category ID to the menu item DTO
        menuItemDto.setCategoryId(category.getId());
        }
        catch(IllegalArgumentException  e){
            // If category exists, add error message and reload categories
            model.addAttribute("categoryError", e.getMessage());
            model.addAttribute("menuItem", menuItemDto);
            model.addAttribute("categories", categoryService.findAll());
            return "form"; // Return to the form with error message        }
    }
}

        if(image.isEmpty()){
            System.out.println("Error");
        }
        menuItemDto.setFile(image);

        if (result.hasErrors()) {
            model.addAttribute("menuItem", menuItemDto);
            model.addAttribute("categories", categoryService.findAll());  // Add categories to the model again
            return "form";
        }
        menuItemService.saveMenuItem(menuItemDto);
        return "redirect:/restaurant/menu";
    }


    @GetMapping("/menu/edit/{id}")
        public String getMenuItem(@PathVariable Long id, Model model) {

        ResMenuItemDto item = menuItemService.findById(id);
        String categoryName = item.getCategoryName();

        model.addAttribute("menuItem", item);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categories", categoryService.findAll());

        return "edit-menu-item"; // Returns the edit page
    }

    @PostMapping("/menu/edit/{id}")
    public String updateMenuItem(@PathVariable Long id, @ModelAttribute ReqMenuItemDto reqMenuItemDto,@RequestParam("image") MultipartFile image) {
        if(!image.isEmpty()){
            reqMenuItemDto.setFile(image);
        }
        System.out.println("id: " + id);
        System.out.println(reqMenuItemDto.getType());
        menuItemService.updateMenuItem(id, reqMenuItemDto);
        return "redirect:/restaurant/menu"; // Redirect back to the menu page
    }

    // Delete menu item
    @GetMapping("/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteById(id);
        return "redirect:/restaurant/menu"; // Redirect to the menu list
}

    // for about page
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
