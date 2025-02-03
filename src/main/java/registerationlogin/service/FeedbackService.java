package registerationlogin.service;
import java.util.List;

import registerationlogin.dto.FeedbackAnalytics;
import registerationlogin.entity.Feedback;

public interface FeedbackService {
    // save feedback
    void saveFeedback(Feedback feedback);
    // get all feedbacks getFeedbackByRestaurant
    List<Feedback> getFeedbackByRestaurant(Long restaurantId);

     // New method for analytics
     FeedbackAnalytics getFeedbackAnalytics(Long restaurantId, int page, int size);
}
