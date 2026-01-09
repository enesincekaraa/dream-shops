package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.exception.category.CategoryNotFoundException;
import com.enesincekara.dreamshops.exception.product.ProductNotFoundException;
import com.enesincekara.dreamshops.mapper.ProductMapper;
import com.enesincekara.dreamshops.request.UpdateProductRequest;
import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.CategoryRepository;
import com.enesincekara.dreamshops.repository.ProductRepository;
import com.enesincekara.dreamshops.request.AddProductRequest;
import com.enesincekara.dreamshops.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProductService implements IProductService{


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    @Override
    public Product addProduct(AddProductRequest req) {
        Category category = categoryRepository.findByName(req.categoryName())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + req.categoryName()));

        Product product = Product.create(
                req.name(),
                req.brand(),
                req.price(),
                req.inventory(),
                req.description(),
                category
        );
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products =  productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products.stream().map(ProductMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id: " + id));
         return ProductMapper.toResponse(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {

        productRepository
                .findById(id)
                .ifPresentOrElse(
                productRepository::delete,
                () -> { throw new ProductNotFoundException("Product not found this id: " + id);});
    }

    @Transactional
    @Override
    public void updateProduct(UpdateProductRequest req, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id: " + productId)
        );
        if (req.name() != null) {
            product.changeName(req.name());
        }
        if (req.brand() != null) {
            product.changeBrand(req.brand());
        }
        if (req.price() != null) {
            product.changePrice(req.price());
        }
        if (req.inventory() != null) {
            product.changeInventory(req.inventory());
        }
        if (req.description() != null) {
            product.changeDescription(req.description());
        }
        if (req.categoryName() != null) {
            Category category = categoryRepository.findByName(req.categoryName()).orElseThrow(
                    ()-> new CategoryNotFoundException("Category not found with name: " + req.categoryName())
            );
            product.changeCategory(category);
        }
        productRepository.save(product);

    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        List<Product>  products = productRepository.findByCategory_Name(category);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategory_NameAndBrand(category, brand);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductsByName(String name) {
        List<Product> products =  productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand,name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public long countProductsByBrandAndName(String brand, String name) {
        long x= productRepository.countByBrandAndName(brand,name);
        if (x == 0){
            throw new ProductNotFoundException("No products found");
        }
        return x;
    }

    @Transactional
    @Override
    public void increaseProductStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id: " + productId)
        );
        product.increaseStock(quantity);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void decreaseProductStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id: " + productId)
        );
        product.decreaseStock(quantity);
        productRepository.save(product);

    }

    @Override
    public void deactivateProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found this id: " + productId)
        );
        product.deactivate();
        productRepository.save(product);
    }

    @Override
    public void activateProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        product.activate();
        productRepository.save(product);

    }

    @Override
    public void softDeleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product not found with id: " + productId)
        );
        product.delete();
        productRepository.save(product);

    }
}
