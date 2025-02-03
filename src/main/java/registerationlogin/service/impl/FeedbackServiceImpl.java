package registerationlogin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import registerationlogin.dto.FeedbackAnalytics;
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

        feedbackRepository.save(feedback);
        updateRestaurantRating(feedback.getRestaurant());
    }

    private void updateRestaurantRating(Restaurant restaurant) {
        List<Feedback> feedbacks = feedbackRepository.findByRestaurantId(restaurant.getId());
        double average = feedbacks.stream()
            .mapToInt(Feedback::getRating)
            .average()
            .orElse(0.0);
        restaurant.setRating((int) Math.round(average));
        // Save restaurant if needed
    }

    @Override
    public List<Feedback> getFeedbackByRestaurant(Long restaurantId) {
        return feedbackRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public FeedbackAnalytics getFeedbackAnalytics(Long restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("submissionDate").descending());
        Page<Feedback> feedbackPage = feedbackRepository.findByRestaurantId(restaurantId, pageable);

        return new FeedbackAnalytics(
                feedbackPage.getContent(),
                calculateAverageRating(feedbackPage.getContent()),
                feedbackPage.getTotalElements(),
                getRecentFeedbackDate(feedbackPage.getContent()),
                calculateRatingDistribution(feedbackPage.getContent()),
                page,
                feedbackPage.getTotalPages());
    }

    private double calculateAverageRating(List<Feedback> feedbacks) {
        if (feedbacks.isEmpty())
            return 0.0;
        return feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
    }

    private Map<Integer, Long> calculateRatingDistribution(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .collect(Collectors.groupingBy(
                        Feedback::getRating,
                        Collectors.counting()));
    }

    private LocalDateTime getRecentFeedbackDate(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(Feedback::getSubmissionDate)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }

}
