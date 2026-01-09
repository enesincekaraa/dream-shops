package com.enesincekara.dreamshops.mapper;

import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.response.ProductResponse;

import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.isActive(),
                product.isInStock(),
                product.isLowStock(),
                product.getDescription(),
                product.getCategory().getName(),
                product.getImages()
                        .stream()
                        .map(Image::getDownloadUrl)
                        .collect(Collectors.toList())
        );
    }
}
