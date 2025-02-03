package registerationlogin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import registerationlogin.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
        Optional<Restaurant> findByName(String name);

        Restaurant findByEmail(String email);

        // Fetch the first 8 restaurants sorted by a certain field (e.g., rating)
        List<Restaurant> findTop8ByOrderByRatingDesc();

        boolean existsByNameIgnoreCase(String name);
        boolean existsByNameIgnoreCaseAndIdNot(String name, Long excludedId);


        // find restaurants which are in 5 km radius of the given latitude and longitude

        @Query(value = "SELECT *, " +
                        "(6371  * acos(cos(radians(:userLat)) * cos(radians(r.latitude)) * " +
                        "cos(radians(r.longitude) - radians(:userLon)) + " +
                        "sin(radians(:userLat)) * sin(radians(r.latitude)))) AS distance " +
                        "FROM Restaurant r " +
                        "HAVING distance < :radius " +
                        "ORDER BY distance", nativeQuery = true)
        List<Restaurant> findNearbyRestaurants(
                        @Param("userLat") double userLat,
                        @Param("userLon") double userLon,
                        @Param("radius") double radius);
}
