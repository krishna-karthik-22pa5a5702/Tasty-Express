package registerationlogin.service;

import registerationlogin.entity.CartItem;
import java.util.List;
import registerationlogin.entity.Order;
import registerationlogin.entity.Restaurant;

public interface OrderService  {
    
    List<CartItem> getCartItemsByIds(List<Long> cartItemIds);
    Order saveOrder(Order order);  
    Restaurant getRestaurantByEmail(String email);  
    List<Order> getOrdersByRestaurantId(Long restaurantId); 
    Order getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String status);
}
