package registerationlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import registerationlogin.entity.Restaurant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private double rating;
    private String imageUrl;

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.rating = restaurant.getRating();
        this.imageUrl = restaurant.getImageUrl();
    }
}
