package registerationlogin.service;

import java.util.List;
import registerationlogin.dto.ReqMenuItemDto;
import registerationlogin.dto.ResMenuItemDto;

public interface MenuItemService {
    List<ResMenuItemDto> findAllItems();
    ResMenuItemDto findById(Long id);
    void saveMenuItem(ReqMenuItemDto menuItemDto);
    void deleteById(Long id);
    void updateMenuItem(Long id,ReqMenuItemDto menuItemDto);
}