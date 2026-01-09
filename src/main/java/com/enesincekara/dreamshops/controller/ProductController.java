package com.enesincekara.dreamshops.controller;

import com.enesincekara.dreamshops.mapper.ProductMapper;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.request.AddProductRequest;
import com.enesincekara.dreamshops.request.StockUpdateRequest;
import com.enesincekara.dreamshops.response.ProductResponse;
import com.enesincekara.dreamshops.service.product.IProductService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ProductResponse createProduct(
            @RequestBody AddProductRequest req
            ){
        Product product = productService.addProduct(req);
        return ProductMapper.toResponse(product);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return productService.searchProducts(brand, category, active, inStock, minPrice, maxPrice);
    }

    @PatchMapping("/{id}/stock/increase")
    public void increaseStock(
            @PathVariable Long id,
            @RequestBody StockUpdateRequest req
    ){
        productService.increaseProductStock(id, req.quantity());
    }

    @PatchMapping("/{id}/stock/decrease")
    public void decreaseStock(
            @PathVariable Long id,
            @RequestBody StockUpdateRequest req
            ){
        productService.decreaseProductStock(id, req.quantity());
    }
}
