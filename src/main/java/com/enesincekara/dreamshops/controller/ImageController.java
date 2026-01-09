package com.enesincekara.dreamshops.controller;


import com.enesincekara.dreamshops.service.image.ImageService;
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
}
