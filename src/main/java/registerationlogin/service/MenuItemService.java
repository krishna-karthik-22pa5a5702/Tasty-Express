package registerationlogin.service;

import java.util.List;

import registerationlogin.dto.MenuItemDto;

public interface MenuItemService {
    List<MenuItemDto> findAllItems();
    MenuItemDto findById(Long id);
    void saveMenuItem(MenuItemDto menuItemDto);
    void deleteById(Long id);
}
