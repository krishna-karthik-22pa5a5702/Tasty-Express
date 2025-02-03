package registerationlogin.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import registerationlogin.entity.Restaurant;
import registerationlogin.service.RestaurantService;

@Controller
@RequestMapping("/restaurant/reports")
public class ReportViewController {

    private RestaurantService restaurantService;

    public ReportViewController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/sales")
    public String showSalesReport(Model model, Principal principal) {
        Long restaurantId = getRestaurantId(principal);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if (restaurant == null) {
            model.addAttribute("error", "Restaurant not found!");
            model.addAttribute("Page", "orders"); // Flag to load Sales Report in content
            return "restaurant-template";
        }

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("Page", "sales"); // Flag to load Sales Report in content
        return "restaurant-template";
    }

    @GetMapping("/customers")
    public String showCustomerInsightsReport(Model model, Principal principal) {

        Long restaurantId = getRestaurantId(principal);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if (restaurant == null) {
            model.addAttribute("error", "Restaurant not found!");
            model.addAttribute("Page", "customers"); // Flag to load Customer Insights Report
            return "restaurant-template";
        }

        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("Page", "customers"); // Flag to load Customer Insights Report
        return "restaurant-template";
    }

    public Long getRestaurantId(Principal principal) {
        String email = principal.getName();
        Restaurant restaurant = restaurantService.getRestaurantByEmail(email);
        return restaurant.getId();
    }

}
