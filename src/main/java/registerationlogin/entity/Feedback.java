package registerationlogin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    private int rating;

    @Column(length = 1000)
    private String comment;

    private LocalDateTime submissionDate; // Store the submission date

    @PrePersist
    public void prePersist() {
        if (this.submissionDate == null) {
            this.submissionDate = LocalDateTime.now(); // Set default to current date-time
        }
    }
}
