package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Cart;
import registerationlogin.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);    
}
