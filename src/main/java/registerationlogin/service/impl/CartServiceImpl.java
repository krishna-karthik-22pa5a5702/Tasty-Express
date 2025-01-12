package registerationlogin.service.impl;

import org.springframework.stereotype.Service;

import registerationlogin.entity.Cart;
import registerationlogin.entity.CartItem;
import registerationlogin.entity.MenuItem;
import registerationlogin.entity.User;
import registerationlogin.repository.CartItemRepository;
import registerationlogin.repository.CartRepository;
import registerationlogin.repository.UserRepository;
import registerationlogin.service.CartService;

@Service
public class CartServiceImpl implements CartService{

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Assuming you have a method to find a user by email
    }

    @Override
    public void addToCart(User user, MenuItem menuItem, Long restaurantId) {
        
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            double total = cart.getTotalPrice();
            total += menuItem.getPrice();
            System.out.println("Total price: " + total);
            cart.setTotalPrice(total);
            cartRepository.save(cart);
        }
        else{
            double total = cart.getTotalPrice();
            total += menuItem.getPrice();
            System.out.println("Total price: " + total);

            cart.setTotalPrice(total);
            cartRepository.save(cart);
        }
              // Create and add a new cart item
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMenuItem(menuItem);
        cartItem.setQuantity(1); // Default to 1, can be updated with a quantity field
        cartItemRepository.save(cartItem);
    }

    @Override
    public Cart getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.getCartItems().clear(); // Remove all cart items
        cart.setTotalPrice(0); // Reset total price
        save(cart); // Persist changes
    }
    
}
