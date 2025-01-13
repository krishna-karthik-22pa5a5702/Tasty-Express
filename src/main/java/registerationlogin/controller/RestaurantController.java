package registerationlogin.controller;

import java.security.Principal;
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
import registerationlogin.entity.Order;
import registerationlogin.entity.Restaurant;
import registerationlogin.service.CategoryService;
import registerationlogin.service.MenuItemService;
import registerationlogin.service.OrderService;
import registerationlogin.service.RestaurantService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private MenuItemService menuItemService;
    private CategoryService categoryService;
    private OrderService orderService;
    private RestaurantService restaurantService;


    public RestaurantController(MenuItemService menuItemService, CategoryService categoryService, OrderService orderService, RestaurantService restaurantService) {
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.restaurantService = restaurantService;
    }
    

   //handler methods for restaurant dashboard.
    @GetMapping("/dashboard")
    public String restaurant(){
        return "restaurant-dashboard";
    }

    @GetMapping("/menu")
        public String menu(Model model, Principal principal) {
            Long restaurantId = getRestaurantId(principal);
            
        List<ResMenuItemDto> menuItems = menuItemService.findAllItems(restaurantId);
        // System.out.println("menuItems: " + menuItems);
        model.addAttribute("menuItems", menuItems);
        return "restaurant-menu-view";
    }

        @GetMapping("/add-menu")
        public String showMenuItemForm(Model model, Principal principal) {
            Long restaurantId = getRestaurantId(principal);

            ReqMenuItemDto menuItemDto = new ReqMenuItemDto();
            model.addAttribute("menuItem", menuItemDto);
            model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));
        return "restaurant-menu-form";
    }

    @PostMapping("/save")
    public String saveMenuItem(@Valid @ModelAttribute("menuItem") ReqMenuItemDto menuItemDto, BindingResult result, Model model ,@RequestParam("image") MultipartFile image,@RequestParam(value = "newCategory", required = false) String newCategory, Principal principal) {

        Long restaurantId = getRestaurantId(principal);
        Restaurant restaurant = getRestaurant(principal);

         // Check if a new category is provided
        if (newCategory != null && !newCategory.trim().isEmpty()) {
        try{
        // Save the new category if it doesn't already exist
        Category category = new Category();
        category.setName(newCategory);
        category.setRestaurant(restaurant);
        categoryService.save(category);

        
        // Set the new category ID to the menu item DTO
        menuItemDto.setCategoryId(category.getId());
        }
        catch(IllegalArgumentException  e){
            // If category exists, add error message and reload categories
            model.addAttribute("categoryError", e.getMessage());
            model.addAttribute("menuItem", menuItemDto);
            // model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));

            return "restaurant-menu-form"; // Return to the form with error message        }
    }
}

        if(image.isEmpty()){
            System.out.println("Error");
        }
        menuItemDto.setFile(image);

        if (result.hasErrors()) {
            model.addAttribute("menuItem", menuItemDto);
            // model.addAttribute("categories", categoryService.findAll());  // Add categories to the model again
            model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));

            return "restaurant-menu-form";
        }
        menuItemService.saveMenuItem(menuItemDto);
        return "redirect:/restaurant/menu";
    }


    @GetMapping("/menu/edit/{id}")
        public String getMenuItem(@PathVariable Long id, Model model, Principal principal) {

        ResMenuItemDto item = menuItemService.findById(id);
        String categoryName = item.getCategoryName();
        Long restaurantId = getRestaurantId(principal);

        model.addAttribute("menuItem", item);
        model.addAttribute("categoryName", categoryName);
        // model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));


        return "restaurant-edit-menu-item"; // Returns the edit page
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

    @GetMapping("/orders")
    public String getRestaurantOrders(Model model, Principal principal) {
        // Get restaurant details from logged-in user (e.g., email or ID)
        String email = principal.getName(); 
        System.out.println("Email: " + email);

        Restaurant restaurant = orderService.getRestaurantByEmail(email);

        if (restaurant == null) {
            model.addAttribute("error", "Restaurant not found!");
            return "restaurant-orders";
        }

        Long RestaurantId = restaurant.getId();
        System.out.println("Restaurant: " + restaurant);

        // Fetch all orders for the restaurant
        List<Order> orders = orderService.getOrdersByRestaurantId(RestaurantId);

        // print all orders
        for (Order order : orders) {
            System.out.println("Order: " + order);
            order.getOrderItems().forEach(item -> {
                System.out.println("Item: " + item);
            });
        }


        model.addAttribute("orders", orders);
        model.addAttribute("restaurant", restaurant);
        return "restaurant-orders"; // Return restaurant orders page
    }

    @PostMapping("/orders/update-status")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return "redirect:/restaurant/orders"; // Redirect to the orders page
        }
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/restaurant/orders"; // Redirect to the orders page
    }

    public Long getRestaurantId(Principal principal) {
        String email = principal.getName();
        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);
        return restaurant.getId();
    }

    public Restaurant getRestaurant(Principal principal) {
        String email = principal.getName();
        return restaurantService.getRestaurantByEmail(email);
    }

}
