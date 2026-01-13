package com.enesincekara.dreamshops.controller.images;

import com.enesincekara.dreamshops.mapper.ImageMapper;
import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.response.image.ImageResponse;
import com.enesincekara.dreamshops.service.image.ImageService;
import org.springframework.http.HttpStatus;
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

    // Upload
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageResponse uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return imageService.uploadImage(productId, file);
    }

    @GetMapping("/{objectKey}")
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable Long productId,
            @PathVariable String objectKey
    ) {
        var downloaded = imageService.downloadImage(productId, objectKey);

        return ResponseEntity.ok()
                .header("Content-Type", downloaded.contentType())
                .body(downloaded.bytes());
    }

    @DeleteMapping("/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(
            @PathVariable Long productId,
            @PathVariable Long imageId
    ){
        imageService.deleteImage(productId,imageId);
    }

}
