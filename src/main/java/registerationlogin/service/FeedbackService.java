package registerationlogin.service;
import java.util.List;

import registerationlogin.entity.Feedback;

public interface FeedbackService {
    // save feedback
    void saveFeedback(Feedback feedback);
    // get all feedbacks getFeedbackByRestaurant
    List<Feedback> getFeedbackByRestaurant(Long restaurantId);
}
