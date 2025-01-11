package registerationlogin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMenuDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean availability;
    private String imageUrl;
    private String categoryName;
    private String type;
    private Integer rating;
}