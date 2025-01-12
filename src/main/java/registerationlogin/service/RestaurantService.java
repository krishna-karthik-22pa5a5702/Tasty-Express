package registerationlogin.service;
import registerationlogin.entity.Restaurant;
import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);
    Restaurant saveRestaurant(Restaurant restaurant);
    void deleteRestaurant(Long id);
}
