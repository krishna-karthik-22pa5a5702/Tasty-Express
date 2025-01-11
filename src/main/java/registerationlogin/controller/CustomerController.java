package registerationlogin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import registerationlogin.entity.MenuItem;
import registerationlogin.service.MenuItemService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private MenuItemService menuItemService;

    public CustomerController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    //handler methods for getting list of restaurant.
    @GetMapping("/home")
    public String customer(){
        return "customer";
    }
    
    @GetMapping("/menu")
    public String menu(Model model) {
        // List<UserMenuDto> menuItems = menuItemService.findAllItemsForUser();
        Map<String, List<MenuItem>> menuItemsGroupedByCategory = menuItemService.findAllItemsGroupByCategory();

        // model.addAttribute("menuItems", menuItems);
        model.addAttribute("menuItemsGroupedByCategory", menuItemsGroupedByCategory);

        return "user-menu";
    }
}
