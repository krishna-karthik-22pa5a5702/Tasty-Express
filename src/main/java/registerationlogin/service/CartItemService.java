package registerationlogin.service;
import registerationlogin.entity.CartItem;
import registerationlogin.entity.Cart;
import java.util.List;

public interface CartItemService {  
    CartItem findById(Long id);  
    void save(CartItem cartItem);
    Double calculateTotalPrice(Cart cart);
    void delete(CartItem cartItem);
    List<CartItem>  getCartItemsByCartId(Long cartId);
}
