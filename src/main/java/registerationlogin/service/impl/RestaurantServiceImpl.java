package registerationlogin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import registerationlogin.entity.Restaurant;
import registerationlogin.repository.RestaurantRepository;
import registerationlogin.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByName(restaurant.getName());

        if (existingRestaurant.isPresent()) {
            throw new IllegalArgumentException("Restaurant with this name already exists.");
        }
        restaurantRepository.save(restaurant); // Save the new restaurant to the database
        return restaurant;
    }


    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
    
}
