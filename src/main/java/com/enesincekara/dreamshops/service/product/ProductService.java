package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.exception.ProductNotFoundException;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.ProductRepository;

import java.util.List;

public class ProductService implements IProductService{


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id {id}" + id));
    }

    @Override
    public void deleteProduct(Long id) {

        productRepository
                .findById(id)
                .ifPresentOrElse(
                productRepository::delete,
                () -> { throw new ProductNotFoundException("Product not found this id {id}" + id);});
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrandName(brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrandName(category, brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products =  productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand,name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        Long x= productRepository.countByBrandAndName(brand,name);
        if (x == 0){
            throw new ProductNotFoundException("No products found");
        }
        return x;
    }
}
