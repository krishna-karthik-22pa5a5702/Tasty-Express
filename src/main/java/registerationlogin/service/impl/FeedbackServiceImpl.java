package registerationlogin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import registerationlogin.entity.Feedback;
import registerationlogin.entity.Restaurant;
import registerationlogin.repository.FeedbackRepository;
import registerationlogin.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void saveFeedback(Feedback feedback) {

        int rating = feedback.getRating();
        Restaurant restaurant = feedback.getRestaurant();
        if (restaurant.getRating() == 0) {
            restaurant.setRating(rating);
        } else {
            restaurant.setRating((restaurant.getRating() + rating) / 2);
        }

        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackByRestaurant(Long restaurantId) {
        return feedbackRepository.findByRestaurantId(restaurantId);
    }

}
