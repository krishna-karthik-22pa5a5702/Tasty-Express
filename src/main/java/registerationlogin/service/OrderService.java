package registerationlogin.service;

import registerationlogin.entity.CartItem;
import java.util.List;
import registerationlogin.entity.Order;

public interface OrderService  {
    
    List<CartItem> getCartItemsByIds(List<Long> cartItemIds);
    Order saveOrder(Order order);     
}
