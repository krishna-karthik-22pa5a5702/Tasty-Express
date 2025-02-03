package registerationlogin.service;

import registerationlogin.entity.Restaurant;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long id);

    Restaurant getRestaurantByEmail(String email);

    // i want only 8 restaraunts to be displayed on the home page its condition will
    // apply in future for now it will return all the restaraunts
    List<Restaurant> getTopRestaurants();

    List<Restaurant> getNearbyRestaurants(double userLat, double userLon, double radius);

    public boolean isNameAlreadyUsed(String name);

    public boolean isNameAlreadyUsed(String name, Long excludedId);

    public Restaurant updateRestaurant(Restaurant existingRestaurant, Restaurant updatedRestaurant,
            MultipartFile imageFile,
            List<String> paymentMethods);

}
