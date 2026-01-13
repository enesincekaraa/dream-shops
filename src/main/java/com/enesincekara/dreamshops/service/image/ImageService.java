package com.enesincekara.dreamshops.service.image;

import com.enesincekara.dreamshops.exception.product.ProductNotFoundException;
import com.enesincekara.dreamshops.mapper.ImageMapper;
import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.ImageRepository;
import com.enesincekara.dreamshops.repository.ProductRepository;
import com.enesincekara.dreamshops.response.image.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ImageService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ImageStorageService storage;

    public ImageService(ProductRepository productRepository,
                        ImageRepository imageRepository,
                        ImageStorageService storage) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.storage = storage;
    }

    public ImageResponse uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

        // 1) binary -> MinIO
        String objectKey = storage.upload(file);

        // 2) metadata -> DB
        String downloadUrl = "/api/products/" + productId + "/images/" + objectKey;

        Image image = Image.create(
                file.getOriginalFilename(),
                file.getContentType(),
                objectKey,
                downloadUrl
        );

        product.addImage(image);
        // cascade ALL varsa product save yeter, ama net olsun:
        Image savedImage =  imageRepository.save(image);

        return ImageMapper.toResponse(savedImage);
    }

    @Transactional(readOnly = true)
    public DownloadedImage downloadImage(Long productId, String objectKey) {
        Image image = imageRepository.findByProductIdAndObjectKey(productId, objectKey)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        byte[] bytes = storage.download(objectKey);

        return new DownloadedImage(image.getContentType(), bytes);
    }

    public void deleteImage(Long productId, String objectKey) {
        Image image = imageRepository.findByProductIdAndObjectKey(productId, objectKey)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Clean yaklaşım: önce MinIO, sonra DB
        storage.delete(objectKey);

        // ilişkiyi kopar
        Product product = image.getProduct();
        product.removeImage(image);

        // orphanRemoval varsa bu satır opsiyonel ama açık yazalım
        imageRepository.delete(image);
    }

    public record DownloadedImage(String contentType, byte[] bytes) {}
}
