package registerationlogin.service.impl;

import org.springframework.stereotype.Service;
import registerationlogin.entity.Cart;
import registerationlogin.entity.CartItem;
import registerationlogin.repository.CartItemRepository;
import registerationlogin.service.CartItemService;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public Double calculateTotalPrice(Cart cart) {
        double totalPrice = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getMenuItem().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
}
