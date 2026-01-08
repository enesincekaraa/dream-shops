package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.exception.product.UpdateProductRequest;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest req);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProduct(Long id);
    void updateProduct(UpdateProductRequest req, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    long countProductsByBrandAndName(String brand, String name);


}
