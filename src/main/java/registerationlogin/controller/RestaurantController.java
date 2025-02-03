package registerationlogin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import registerationlogin.dto.FeedbackAnalytics;
import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;
import registerationlogin.entity.Category;
import registerationlogin.entity.Order;
import registerationlogin.entity.Restaurant;
import registerationlogin.repository.UserRepository;
import registerationlogin.service.CategoryService;
import registerationlogin.service.CloudinaryService;
import registerationlogin.service.FeedbackService;
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
    private FeedbackService feedbackService;

    public RestaurantController(MenuItemService menuItemService, CategoryService categoryService,
            OrderService orderService, RestaurantService restaurantService, FeedbackService feedbackService) {
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.restaurantService = restaurantService;
        this.feedbackService = feedbackService;
    }

    // handler methods for restaurant dashboard.
    @GetMapping("/dashboard")
    public String restaurant(Model model) {
        model.addAttribute("Page", "dashboard"); // Flag to load Sales Report in content
        return "restaurant-template";
    }

    @GetMapping("/menu")
    public String menu(Model model, Principal principal) {
        Long restaurantId = getRestaurantId(principal);

        List<ResMenuItemDto> menuItems = menuItemService.findAllItems(restaurantId);
        // System.out.println("menuItems: " + menuItems);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("Page", "view-menu"); // Flag to load Sales Report in content
        return "restaurant-template";
    }

    @GetMapping("/add-menu")
    public String showMenuItemForm(Model model, Principal principal) {
        Long restaurantId = getRestaurantId(principal);

        ReqMenuItemDto menuItemDto = new ReqMenuItemDto();
        model.addAttribute("menuItem", menuItemDto);
        model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));
        model.addAttribute("Page", "menu-form"); // Flag to load Sales Report in content

        return "restaurant-template";
    }

    @PostMapping("/save")
    public String saveMenuItem(@Valid @ModelAttribute("menuItem") ReqMenuItemDto menuItemDto, BindingResult result,
            Model model, @RequestParam("image") MultipartFile image,
            @RequestParam(value = "newCategory", required = false) String newCategory, Principal principal) {

        Long restaurantId = getRestaurantId(principal);
        Restaurant restaurant = getRestaurant(principal);

        // Check if a new category is provided
        if (newCategory != null && !newCategory.trim().isEmpty()) {
            try {
                // Save the new category if it doesn't already exist
                Category category = new Category();
                category.setName(newCategory);
                category.setRestaurant(restaurant);
                categoryService.save(category);

                // Set the new category ID to the menu item DTO
                menuItemDto.setCategoryId(category.getId());
            } catch (IllegalArgumentException e) {
                // If category exists, add error message and reload categories
                model.addAttribute("categoryError", e.getMessage());
                model.addAttribute("menuItem", menuItemDto);
                // model.addAttribute("categories", categoryService.findAll());
                model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));
                model.addAttribute("Page", "menu-form"); // Flag to load Sales Report in content
                return "restaurant-template"; // Return to the form with error message }
            }
        }

        if (image.isEmpty()) {
            System.out.println("Error");
        }
        menuItemDto.setFile(image);

        if (result.hasErrors()) {
            model.addAttribute("menuItem", menuItemDto);
            // model.addAttribute("categories", categoryService.findAll()); // Add
            // categories to the model again
            model.addAttribute("categories", categoryService.findAllByRestaurantId(restaurantId));
            model.addAttribute("Page", "menu-form"); // Flag to load Sales Report in content

            return "restaurant-template";
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
    public String updateMenuItem(@PathVariable Long id, @ModelAttribute ReqMenuItemDto reqMenuItemDto,
            @RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
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
    public String about() {
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
        model.addAttribute("Page", "orders"); // Flag to load Sales Report in content
        return "restaurant-template"; // Return restaurant orders page
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

    // @GetMapping("/feedback")
    // public String showFeedbacks(Model model, Principal principal) {
    // String email = principal.getName();
    // System.out.println("Email: " + email);

    // Long restaurantId = getRestaurantId(principal);

    // System.out.println("Restaurant ID: " + restaurantId);

    // List<Feedback> feedbacks =
    // feedbackService.getFeedbackByRestaurant(restaurantId);

    // // prind date of feedback
    // for (Feedback feedback : feedbacks) {
    // System.out.println("Feedback: " + feedback);
    // System.out.println("Date: " + feedback.getSubmissionDate());
    // }

    // model.addAttribute("feedbacks", feedbacks);
    // model.addAttribute("Page", "feedbacks"); // Flag to load Sales Report in
    // content
    // return "restaurant-template"; // The name of the feedback display page for
    // restaurants
    // }

    @GetMapping("/feedback")
    public String showFeedbacks(
            Model model,
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long restaurantId = getRestaurantId(principal);

        FeedbackAnalytics analytics = feedbackService.getFeedbackAnalytics(
                restaurantId,
                page,
                size);

        model.addAttribute("feedbacks", analytics.feedbacks());
        // Ensure averageRating is formatted with decimal places
        model.addAttribute("averageRating",
                Math.round(analytics.averageRating() * 10) / 10.0); // Rounds to 1 decimal
        // model.addAttribute("averageRating", analytics.averageRating());
        model.addAttribute("totalFeedbacks", analytics.totalFeedbacks());
        model.addAttribute("recentFeedbackDate", analytics.recentFeedbackDate());
        model.addAttribute("ratingDistribution", analytics.ratingDistribution());
        model.addAttribute("currentPage", analytics.currentPage());
        model.addAttribute("totalPages", analytics.totalPages());

        model.addAttribute("Page", "feedbacks");
        return "restaurant-template";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {

        Restaurant restaurant = getRestaurant(principal);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("paymentMethods", List.of(
                "Cash", "Credit Card", "Debit Card", "UPI", "Mobile Payment"));

        return "restaurant-profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("restaurant") Restaurant updatedRestaurant,
            BindingResult result,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("paymentMethods") List<String> paymentMethods,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Restaurant existing = getRestaurant(principal); // Get current restaurant

        try {
            restaurantService.updateRestaurant(existing, updatedRestaurant, imageFile, paymentMethods);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        } catch (IllegalArgumentException ex) {
            result.rejectValue("name", "duplicate.name", ex.getMessage());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.restaurant", result);
            redirectAttributes.addFlashAttribute("restaurant", updatedRestaurant);
        }

        return "redirect:/restaurant/profile";
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