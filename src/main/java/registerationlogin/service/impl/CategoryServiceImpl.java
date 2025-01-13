package registerationlogin.service.impl;


import org.springframework.stereotype.Service;
import registerationlogin.entity.Category;
import registerationlogin.repository.CategoryRepository;
import registerationlogin.service.CategoryService;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private  CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
    @Override
    public Category save(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());

        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists.");
        }
        categoryRepository.save(category); // Save the new category to the database
        return category;
    }

    @Override
    public List<Category> findAllByRestaurantId(Long restaurantId) {
        return categoryRepository.findByRestaurantId(restaurantId);
    }
}

