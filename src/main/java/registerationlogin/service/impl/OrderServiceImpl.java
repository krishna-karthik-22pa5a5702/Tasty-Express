package registerationlogin.service.impl;

import registerationlogin.entity.CartItem;
import registerationlogin.repository.CartItemRepository;
import registerationlogin.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import registerationlogin.entity.Order;
import registerationlogin.entity.Restaurant;
import registerationlogin.repository.OrderRepository;
import registerationlogin.repository.RestaurantRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private  CartItemRepository cartItemRepository;
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;



    public OrderServiceImpl(CartItemRepository cartItemRepository, OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<CartItem> getCartItemsByIds(List<Long> cartItemIds) {
        return cartItemRepository.findAllById(cartItemIds);
    }
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order); // Save the order to the database
    }

    @Override
    public Restaurant getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus(status);
            orderRepository.save(order);
        }
        System.out.println("Order status updated to: " + order.getOrderStatus());
        System.out.println("Order status updated to: " + status);
    }
    
}
