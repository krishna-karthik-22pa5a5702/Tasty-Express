package registerationlogin.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import registerationlogin.dto.ImageModel;

public interface ImageService {
      ResponseEntity<Map> uploadImage(ImageModel imageModel);
}
