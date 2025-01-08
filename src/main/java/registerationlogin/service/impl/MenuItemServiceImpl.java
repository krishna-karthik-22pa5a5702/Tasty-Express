package registerationlogin.service.impl;

import org.springframework.stereotype.Service;

import registerationlogin.dto.MenuItemDto;
import registerationlogin.entity.MenuItem;
import registerationlogin.repository.MenuItemRepository;
import registerationlogin.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private  MenuItemRepository menuItemRepository;
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItemDto> findAllItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        List<MenuItemDto> menuItemDTOs = new ArrayList<>();
        
        for (MenuItem menuItem : menuItems) {
            MenuItemDto menuItemDTO = new MenuItemDto(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailability()                );
                menuItemDTOs.add(menuItemDTO);
            }
    return menuItemDTOs;
}
    @Override
    public MenuItemDto findById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElse(null);
        if (menuItem != null) {
            return new MenuItemDto(
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailability()
                            );
        }
        return null;    }

    @Override
    public void saveMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setAvailability(menuItemDto.getAvailability());
        menuItemRepository.save(menuItem);

    }

    @Override
    public void deleteById(Long id) {
        menuItemRepository.deleteById(id);
    }
}
