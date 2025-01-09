package registerationlogin.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReqMenuItemDto {
    private String name;
    private String description;
    private Double price;
    private Boolean availability;
    // new changes 
    private MultipartFile file;
    private Long categoryId;

}
