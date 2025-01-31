package registerationlogin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import registerationlogin.entity.Feedback;

import java.util.List;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByRestaurantId(Long restaurantId);
}
