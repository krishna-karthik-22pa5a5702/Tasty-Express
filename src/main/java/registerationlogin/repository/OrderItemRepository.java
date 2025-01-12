package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {    
}
