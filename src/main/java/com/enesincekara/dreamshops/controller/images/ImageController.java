package com.enesincekara.dreamshops.controller.images;


import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.service.image.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products/{productId}/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public void uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file) throws IOException {
        imageService.uploadImage(productId, file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable Long productId,
            @PathVariable String fileName
    ){
        Image image = imageService.downloadImage(productId, fileName);

        return  ResponseEntity.ok()
                .header("Content-Type",image.getFileType())
                .body(image.getImage());
    }

}
