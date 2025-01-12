package registerationlogin.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Category;
import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findByRestaurantId(Long restaurantId);
}
