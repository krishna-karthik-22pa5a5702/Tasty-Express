package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
}
