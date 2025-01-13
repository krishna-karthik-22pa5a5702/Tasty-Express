package registerationlogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {  
    Optional<Restaurant> findByName(String name);
    Restaurant findByEmail(String email);
}
