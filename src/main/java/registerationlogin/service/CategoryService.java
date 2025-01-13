package registerationlogin.service;

import registerationlogin.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    List<Category> findAllByRestaurantId(Long restaurantId);
}
