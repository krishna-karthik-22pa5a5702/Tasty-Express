package registerationlogin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import registerationlogin.dto.RestaurantDTO;
import registerationlogin.service.RestaurantService;

@RestController
public class LocationController {

    private final RestaurantService restaurantService;

    public LocationController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @CrossOrigin(origins = "*") // Allow all origins
    @GetMapping("/customer/restaurants/nearby")
    public ResponseEntity<List<RestaurantDTO>> getNearbyRestaurants(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1") double radius) {
        // add edge case for if restaurant is not found

        List<RestaurantDTO> nearbyRestaurants = restaurantService.getNearbyRestaurants(latitude, longitude, radius)
                .stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());

        if (nearbyRestaurants.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>()); // Returning an empty list
        }
        return ResponseEntity.ok(nearbyRestaurants);
    }
}
