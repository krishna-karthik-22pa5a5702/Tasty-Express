package registerationlogin.service.impl;

import registerationlogin.entity.Cart;
import registerationlogin.entity.CartItem;
import registerationlogin.repository.CartItemRepository;
import registerationlogin.repository.CartRepository;
import registerationlogin.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import registerationlogin.entity.Order;
import registerationlogin.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private  CartItemRepository cartItemRepository;
    private OrderRepository orderRepository;
    private CartRepository cartRepository;


    public OrderServiceImpl(CartItemRepository cartItemRepository, OrderRepository orderRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartItem> getCartItemsByIds(List<Long> cartItemIds) {
        return cartItemRepository.findAllById(cartItemIds);
    }
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order); // Save the order to the database
    }
    
}
