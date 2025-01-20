package registerationlogin.service;
import registerationlogin.entity.Restaurant;
import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);
    Restaurant saveRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long id);
    Restaurant getRestaurantByEmail(String email);
    // i want only 8 restaraunts to be displayed on the home page its condition will apply in future for now it will return all the restaraunts
    List<Restaurant> getTopRestaurants();
}
