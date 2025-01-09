package registerationlogin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import registerationlogin.dto.ImageModel;
import registerationlogin.entity.Image;
import registerationlogin.repository.ImageRepository;
import registerationlogin.service.ImageService;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Controller
public class ImageController {
    private ImageService imageService;
    private ImageRepository imageRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    // Display image upload form
    @GetMapping("/restaurant/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("imageModel", new ImageModel());
        return "upload"; // Refers to upload.html in the templates folder
    }

    // Handle image upload
    @PostMapping("/restaurant/upload")
    public String uploadImage(ImageModel imageModel, Model model) {
        try {
            ResponseEntity<Map> response = imageService.uploadImage(imageModel);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("success", "Image uploaded successfully!");
                model.addAttribute("imageUrl", response.getBody().get("url"));
            } else {
                model.addAttribute("error", "Image upload failed. Please try again.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during upload: " + e.getMessage());
        }
        return "upload"; // Reload the upload form with messages
    }

    // Display all uploaded images
    @GetMapping("/restaurant/images")
    public String listUploadedImages(Model model) {
        List<Image> images = imageRepository.findAll();
        model.addAttribute("images", images);
        return "images"; // Refers to images.html in the templates folder
    }
    
}
