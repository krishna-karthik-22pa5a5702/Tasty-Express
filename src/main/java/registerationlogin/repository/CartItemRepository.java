package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import registerationlogin.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);
    
}
