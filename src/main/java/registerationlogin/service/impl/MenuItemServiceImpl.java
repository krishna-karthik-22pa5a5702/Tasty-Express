package registerationlogin.service.impl;

import org.springframework.stereotype.Service;

import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;
import registerationlogin.entity.Category;
import registerationlogin.entity.MenuItem;
import registerationlogin.repository.MenuItemRepository;
import registerationlogin.service.CategoryService;
import registerationlogin.service.CloudinaryService;
import registerationlogin.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private  MenuItemRepository menuItemRepository;
    private CloudinaryService cloudinaryService;
    private CategoryService categoryService;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,CloudinaryService cloudinaryService,CategoryService categoryService) {
        this.menuItemRepository = menuItemRepository;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;

    }

    @Override
    public List<ResMenuItemDto> findAllItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<ResMenuItemDto> menuItemDTOs = new ArrayList<>();
        
        for (MenuItem menuItem : menuItems) {
            // get category name from category object
            String categoryName= menuItem.getCategory().getName();

            ResMenuItemDto menuItemDTO = new ResMenuItemDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailability(),
                menuItem.getImageUrl(),
                categoryName
                );
                
                menuItemDTOs.add(menuItemDTO);
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
                menuItem.getCategory().getName()
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
            if(menuItemDto.getFile() == null){
                menuItem.setImageUrl(menuItem.getImageUrl());
            }
            else{
                menuItem.setImageUrl(cloudinaryService.uploadFile(menuItemDto.getFile(), "folder_1"));
            }
            menuItemRepository.save(menuItem);
        }
    }
}
