package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.exception.ProductNotFoundException;
import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.ProductRepository;
import com.enesincekara.dreamshops.request.AddProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProductService implements IProductService{


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


//    @Transactional
//    @Override
//    public Product addProduct(AddProductRequest req) {
//        Category category = categoryRepository.findByName(req.categoryName())
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//
//        Product product = Product.create(
//                req.name(),
//                req.brand(),
//                req.price(),
//                req.inventory(),
//                req.description(),
//                category
//        );
//        productRepository.save(product);
//        return product;
//
//    }


    @Override
    public Product addProduct(AddProductRequest req) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id {id}" + id));
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {

        productRepository
                .findById(id)
                .ifPresentOrElse(
                productRepository::delete,
                () -> { throw new ProductNotFoundException("Product not found this id {id}" + id);});
    }

    @Transactional
    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrandName(brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrandName(category, brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products =  productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand,name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        Long x= productRepository.countByBrandAndName(brand,name);
        if (x == 0){
            throw new ProductNotFoundException("No products found");
        }
        return x;
    }
}
