package registerationlogin.service;

import java.util.List;
import java.util.Map;

import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;
import registerationlogin.dto.UserMenuDto;
import registerationlogin.entity.MenuItem;

public interface MenuItemService {
    List<ResMenuItemDto> findAllItems();
    ResMenuItemDto findById(Long id);
    void saveMenuItem(ReqMenuItemDto menuItemDto);
    void deleteById(Long id);
    void updateMenuItem(Long id,ReqMenuItemDto menuItemDto);
    List<UserMenuDto> findAllItemsForUser();
    Map<String, List<MenuItem>> findAllItemsGroupByCategory();
}