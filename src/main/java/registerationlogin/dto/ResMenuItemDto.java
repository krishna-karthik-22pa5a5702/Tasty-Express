package registerationlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResMenuItemDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean availability;
    // new changes 
    private String imageUrl;
    private String categoryName;
    private String type;
}
