package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.request.UpdateProductRequest;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.request.AddProductRequest;
import com.enesincekara.dreamshops.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest req);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    void deleteProduct(Long id);
    void updateProduct(UpdateProductRequest req, Long productId);
    List<ProductResponse> getProductsByCategory(String category);
    List<ProductResponse> getProductsByBrand(String brand);
    List<ProductResponse> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductResponse> getProductsByName(String name);
    List<ProductResponse> getProductsByBrandAndName(String brand, String name);
    long countProductsByBrandAndName(String brand, String name);
    void increaseProductStock(Long productId, int quantity);
    void decreaseProductStock(Long productId, int quantity);
    void deactivateProduct(Long productId);
    void activateProduct(Long productId);
    void softDeleteProduct(Long productId);
    List<ProductResponse> searchProducts(
            String brand,
            String category,
            Boolean active,
            Boolean inStock,
            BigDecimal minPrice,
            BigDecimal maxPrice);



}
