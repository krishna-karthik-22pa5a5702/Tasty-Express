package registerationlogin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByRestaurantId(Long restaurantId);
    Page<Feedback> findByRestaurantId(Long restaurantId, Pageable pageable);

}
