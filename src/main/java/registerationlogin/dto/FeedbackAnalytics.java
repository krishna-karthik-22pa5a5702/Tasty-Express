package registerationlogin.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import registerationlogin.entity.Feedback;

public record FeedbackAnalytics(
    List<Feedback> feedbacks,
    double averageRating,
    long totalFeedbacks,
    LocalDateTime recentFeedbackDate,
    Map<Integer, Long> ratingDistribution,
    int currentPage,
    int totalPages
) {}