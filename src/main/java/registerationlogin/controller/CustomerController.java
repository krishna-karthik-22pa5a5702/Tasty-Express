package registerationlogin.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import registerationlogin.entity.MenuItem;
import registerationlogin.entity.Restaurant;
import registerationlogin.entity.Role;
import registerationlogin.entity.User;
import registerationlogin.repository.RoleRepository;
import registerationlogin.repository.UserRepository;
import registerationlogin.service.CartService;
import registerationlogin.service.MenuItemService;
import registerationlogin.service.RestaurantService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private MenuItemService menuItemService;
    private RestaurantService restaurantService;
    private CartService cartService;
    private RoleRepository roleRepository;  // Inject the RoleRepository
    private UserRepository userRepository; // Inject the UserRepository

    public CustomerController(MenuItemService menuItemService, RestaurantService restaurantService, CartService cartService, RoleRepository roleRepository, UserRepository userRepository) {
        this.menuItemService = menuItemService;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    //handler methods for getting list of restaurant.
    @GetMapping("/home")
    public String customer(){
        return "customer";
    }

    @GetMapping("/restaurant-list")
    public String restaurantList(Model model){  
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "user-dashboard";
    }

    @GetMapping("/restaurants/new")
    public String newRestaurant(Model model){
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant_form";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,Principal principal){
        String email = principal.getName();
        User user = cartService.getUserByEmail(email);

        Role restaurantRole = roleRepository.findByName("ROLE_RESTAURANT");

        // Add the RESTAURANT role to the user
    if (!user.getRoles().contains(restaurantRole)) {
        user.getRoles().add(restaurantRole);
    }

    userRepository.save(user);

        restaurantService.saveRestaurant(restaurant);
        return "redirect:/restaurant/restaurant-dashboard";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        // List<UserMenuDto> menuItems = menuItemService.findAllItemsForUser();
        Map<String, List<MenuItem>> menuItemsGroupedByCategory = menuItemService.findAllItemsGroupByCategory();
        // model.addAttribute("menuItems", menuItems);
        model.addAttribute("menuItemsGroupedByCategory", menuItemsGroupedByCategory);

        return "user-menu";
    }

    @GetMapping("/restaurant-menu/{restaurantId}")
    public String restaurantMenu(@PathVariable Long restaurantId,Model model) {
        // List<UserMenuDto> menuItems = menuItemService.findAllItemsForUser();
        Map<String, List<MenuItem>> menuItemsGroupedByCategory = menuItemService.findAllItemsGroupByCategoryByRestaurantId(restaurantId);
        // model.addAttribute("menuItems", menuItems);
        model.addAttribute("menuItemsGroupedByCategory", menuItemsGroupedByCategory);
        model.addAttribute("restaurantId", restaurantId);
        return "user-menu";
    }

    @PostMapping("/menu/add-to-cart")
    public String addToCart(@RequestParam("menuItemId") Long menuItemId, 
                        @RequestParam("restaurantId") Long restaurantId, 
                        Principal principal, 
                        RedirectAttributes redirectAttributes){

                            String email = principal.getName(); // Assuming the email is used for authentication
                            User user = cartService.getUserByEmail(email);
                            System.out.println("This is the user email: " + email);

                            if (user == null) {
                                redirectAttributes.addFlashAttribute("error", "User not found");
                                return "redirect:/login"; // Redirect to login if the user is not found
                            }

                            MenuItem menuItem = menuItemService.findByMenuItemId(menuItemId); // Fetch the menu item by ID

                            if (menuItem == null) {
                                redirectAttributes.addFlashAttribute("error", "Menu item not found");
                                return "redirect:/customer/restaurant-menu/" + restaurantId;
                            }

                            cartService.addToCart(user, menuItem, restaurantId);

                            System.out.println("menuItemId: " + menuItemId);
                            System.out.println("This is the menu restaurant id: " + restaurantId);

        return "redirect:/customer/restaurant-menu/" + restaurantId;
    }


}
