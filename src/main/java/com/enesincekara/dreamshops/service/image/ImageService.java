package com.enesincekara.dreamshops.service.image;

import com.enesincekara.dreamshops.exception.product.ProductNotFoundException;
import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.ImageRepository;
import com.enesincekara.dreamshops.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class ImageService implements IImageService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public ImageService(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }


    @Override
    public void uploadImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found with id " + productId)
        );

        Image image = new Image();

        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setImage(file.getBytes());
        image.setDownloadUrl("/api/products/" + productId + "/images/" + file.getOriginalFilename());

        product.addImage(image);
        imageRepository.save(image);
    }
}
