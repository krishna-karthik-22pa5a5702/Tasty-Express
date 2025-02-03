package registerationlogin.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import registerationlogin.dto.ImageModel;
import registerationlogin.entity.Image;
import registerationlogin.repository.ImageRepository;
import registerationlogin.service.CloudinaryService;
import registerationlogin.service.ImageService;
import java.util.Map;


@Service
public class ImageServiceImpl implements ImageService {

    private CloudinaryService cloudinaryService;
    private ImageRepository imageRepository;

    public ImageServiceImpl(CloudinaryService cloudinaryService, ImageRepository imageRepository) {
        this.cloudinaryService = cloudinaryService;
        this.imageRepository = imageRepository;
    }

    // @Autowired
    // private CloudinaryService cloudinaryService;
    // @Autowired
    // private ImageRepository imageRepository;
    
    @Override
    public ResponseEntity<Map> uploadImage(ImageModel imageModel) {
        try {
            if (imageModel.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
            image.setName(imageModel.getName());
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
