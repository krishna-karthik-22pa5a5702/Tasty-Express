package registerationlogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import registerationlogin.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByCategory_Id(Long categoryId);
    
}
