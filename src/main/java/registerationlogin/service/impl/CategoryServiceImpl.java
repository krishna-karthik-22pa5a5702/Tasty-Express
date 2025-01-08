package registerationlogin.service.impl;


import org.springframework.stereotype.Service;
import registerationlogin.entity.Category;
import registerationlogin.repository.CategoryRepository;
import registerationlogin.service.CategoryService;
import java.util.List;

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
}

