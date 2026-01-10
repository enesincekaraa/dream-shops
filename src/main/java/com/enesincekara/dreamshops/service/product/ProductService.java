package com.enesincekara.dreamshops.service.product;

import com.enesincekara.dreamshops.exception.category.CategoryNotFoundException;
import com.enesincekara.dreamshops.exception.product.ProductNotFoundException;
import com.enesincekara.dreamshops.mapper.ProductMapper;
import com.enesincekara.dreamshops.repository.specification.ProductSpecifications;
import com.enesincekara.dreamshops.request.UpdateProductRequest;
import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.repository.CategoryRepository;
import com.enesincekara.dreamshops.repository.ProductRepository;
import com.enesincekara.dreamshops.request.AddProductRequest;
import com.enesincekara.dreamshops.response.products.PageResponse;
import com.enesincekara.dreamshops.response.products.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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



    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(
                ProductSpecifications.notDeleted(),
                pageable
        );
        if (page.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }

        List<ProductResponse> content=
                page
                        .getContent()
                        .stream()
                        .map(ProductMapper::toResponse)
                        .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> searchProducts(String brand, String category, Boolean active, Boolean inStock, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec =
                ProductSpecifications
                        .notDeleted()
                        .and(ProductSpecifications.hasBrand(brand))
                        .and(ProductSpecifications.hasCategory(category))
                        .and(ProductSpecifications.isActive(active))
                        .and(ProductSpecifications.inStock(inStock))
                        .and(ProductSpecifications.priceBetween(minPrice, maxPrice));

        Page<Product> page = productRepository.findAll(spec, pageable);
        if (page.isEmpty()) {
            throw new ProductNotFoundException("No products found for given filters");
        }
        List<ProductResponse> content = page.getContent()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
