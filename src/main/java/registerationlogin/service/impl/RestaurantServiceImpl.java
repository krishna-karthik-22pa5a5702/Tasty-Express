package registerationlogin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import registerationlogin.entity.Restaurant;
import registerationlogin.repository.RestaurantRepository;
import registerationlogin.service.CloudinaryService;
import registerationlogin.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private CloudinaryService cloudinaryService;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, CloudinaryService cloudinaryService) {
        this.restaurantRepository = restaurantRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // i want only 8 restaraunts to be displayed on the home page its condition will
    // apply in future for now it will return all the restaraunts
    @Override
    public List<Restaurant> getTopRestaurants() {
        return restaurantRepository.findTop8ByOrderByRatingDesc();
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

        if (restaurant.getRating() == null) {
            restaurant.setRating(0);
        }
        restaurantRepository.save(restaurant); // Save the new restaurant to the database
        return restaurant;
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    @Override
    public List<Restaurant> getNearbyRestaurants(double latitude, double longitude, double radius) {

        return restaurantRepository.findNearbyRestaurants(latitude, longitude, radius);
    }

    @Override
    public boolean isNameAlreadyUsed(String name) {
        return restaurantRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean isNameAlreadyUsed(String name, Long excludedId) {
        return restaurantRepository.existsByNameIgnoreCaseAndIdNot(name, excludedId);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant existingRestaurant,
            Restaurant updatedRestaurant,
            MultipartFile imageFile,
            List<String> paymentMethods) {
        // Check if name was changed and validate uniqueness
        if (!existingRestaurant.getName().equalsIgnoreCase(updatedRestaurant.getName())) {
            validateNameUniqueness(updatedRestaurant.getName(), existingRestaurant.getId());
        }

        // Update fields
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setOwnerName(updatedRestaurant.getOwnerName());
        existingRestaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        existingRestaurant.setAlternateContact(updatedRestaurant.getAlternateContact());
        existingRestaurant.setAddress(updatedRestaurant.getAddress());
        existingRestaurant.setLatitude(updatedRestaurant.getLatitude());
        existingRestaurant.setLongitude(updatedRestaurant.getLongitude());
        existingRestaurant.setBankName(updatedRestaurant.getBankName());
        existingRestaurant.setAccountNumber(updatedRestaurant.getAccountNumber());
        existingRestaurant.setAcceptedPaymentMethods(String.join(",", paymentMethods));

        // Handle image upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(imageFile, "folder_1");
            System.out.println("Image URL: " + imageUrl);
            existingRestaurant.setImageUrl(imageUrl);
        }
        else{
            System.out.println("Image is empty");
        }

        return restaurantRepository.save(existingRestaurant);
    }

    private void validateNameUniqueness(String name, Long excludedId) {
        if (restaurantRepository.existsByNameIgnoreCaseAndIdNot(name, excludedId)) {
            throw new IllegalArgumentException("Restaurant with this name already exists");
        }
    }

}
