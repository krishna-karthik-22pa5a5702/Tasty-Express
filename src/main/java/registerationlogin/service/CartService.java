package registerationlogin.service;

import registerationlogin.entity.MenuItem;
import registerationlogin.entity.User;
import registerationlogin.entity.Cart;

public interface CartService {
    User getUserByEmail(String email);
    void addToCart(User user, MenuItem menuItem, Long restaurantId);
    Cart getUserCart(User user);
    void save(Cart cart);
    void clearCart(Cart cart);
}
