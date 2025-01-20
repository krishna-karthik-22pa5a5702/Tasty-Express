package registerationlogin.service.impl;

import org.springframework.stereotype.Service;

import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;
import registerationlogin.dto.UserMenuDto;
import registerationlogin.entity.Category;
import registerationlogin.entity.MenuItem;
import registerationlogin.repository.CategoryRepository;
import registerationlogin.repository.MenuItemRepository;
import registerationlogin.service.CategoryService;
import registerationlogin.service.CloudinaryService;
import registerationlogin.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private  MenuItemRepository menuItemRepository;
    private  CategoryRepository categoryRepository;
    private CloudinaryService cloudinaryService;
    private CategoryService categoryService;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,CloudinaryService cloudinaryService,CategoryService categoryService,CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<ResMenuItemDto> findAllItems(Long restaurantId) {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<ResMenuItemDto> menuItemDTOs = new ArrayList<>();
        
        for (MenuItem menuItem : menuItems) {
            // get category name from category object
            String categoryName= menuItem.getCategory().getName();
            Long restaurantIdFromMenuItem = menuItem.getCategory().getRestaurant().getId();
 
            if(restaurantIdFromMenuItem.equals(restaurantId)){
                ResMenuItemDto menuItemDTO = new ResMenuItemDto(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getDescription(),
                    menuItem.getPrice(),
                    menuItem.getAvailability(),
                    menuItem.getImageUrl(),
                    categoryName,
                    menuItem.getType()
                    );
                    menuItemDTOs.add(menuItemDTO);
            } 
            }
    return menuItemDTOs;
}
    @Override
    public ResMenuItemDto findById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElse(null);
        if (menuItem != null) {
            
            return new ResMenuItemDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailability(),
                menuItem.getImageUrl(),
                menuItem.getCategory().getName(),
                menuItem.getType()
                            );
        }
        return null;    }

    

    @Override
    public void saveMenuItem(ReqMenuItemDto menuItemDto) {

        Category category = categoryService.findById(menuItemDto.getCategoryId());

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setAvailability(menuItemDto.getAvailability());
        menuItem.setImageUrl(cloudinaryService.uploadFile(menuItemDto.getFile(), "folder_1"));
        menuItem.setCategory(category);
        menuItem.setType(menuItemDto.getType());

        menuItemRepository.save(menuItem);

    }

    @Override
    public void deleteById(Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public void updateMenuItem(Long id, ReqMenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findById(id).orElse(null);
        if (menuItem != null) {
            menuItem.setName(menuItemDto.getName());
            menuItem.setDescription(menuItemDto.getDescription());
            menuItem.setPrice(menuItemDto.getPrice());
            menuItem.setAvailability(menuItemDto.getAvailability());
            menuItem.setCategory(categoryService.findById(menuItemDto.getCategoryId()));
            menuItem.setType(menuItemDto.getType());
            if(menuItemDto.getFile() == null){
                menuItem.setImageUrl(menuItem.getImageUrl());
            }
            else{
                menuItem.setImageUrl(cloudinaryService.uploadFile(menuItemDto.getFile(), "folder_1"));
            }
            menuItemRepository.save(menuItem);
        }
    }

    @Override
    public List<UserMenuDto> findAllItemsForUser() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<UserMenuDto> menuItemDTOs = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            UserMenuDto menuItemDTO = new UserMenuDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailability(),
                menuItem.getImageUrl(),
                menuItem.getCategory().getName(),
                menuItem.getType(),
                menuItem.getRating()
            );
            menuItemDTOs.add(menuItemDTO);
        }
        return menuItemDTOs;
    }

    @Override
    public Map<String, List<MenuItem>> findAllItemsGroupByCategory() {

        // Fetch all categories
        List<Category> categories = categoryRepository.findAll();

        // Fetch menu items grouped by category
         return categories.stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        category -> menuItemRepository.findByCategory_Id(category.getId())
                ));
    }

    @Override
    public Map<String, List<MenuItem>> findAllItemsGroupByCategoryByRestaurantId(Long restaurantId) {

        // Fetch all categories
        List<Category> categories = categoryRepository.findByRestaurantId( restaurantId);

        // Fetch menu items grouped by category
         return categories.stream()
                .collect(Collectors.toMap(
                        Category::getName,
                        category -> menuItemRepository.findByCategory_Id(category.getId())
                ));
    }

    @Override
    public MenuItem findByMenuItemId(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }
}
