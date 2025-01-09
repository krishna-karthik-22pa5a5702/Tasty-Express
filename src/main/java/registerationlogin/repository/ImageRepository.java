package registerationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Image;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    
}
