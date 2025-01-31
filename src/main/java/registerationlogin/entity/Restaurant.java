package registerationlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // step 1
    private String name;
    private String ownerName;
    private String phoneNumber;
    private String alternateContact;
    private String email;
    // step 2
    private String address;
    private String latitude;
    private String longitude;
    // step 3
    private String acceptedPaymentMethods;  // Comma-separated string for payment methods
    private String bankName;
    private String accountNumber;

    // step 4
    private String imageUrl;


    private Integer rating;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;  // A restaurant can have many orders
}
