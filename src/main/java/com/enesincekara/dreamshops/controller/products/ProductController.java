package com.enesincekara.dreamshops.controller.products;

import com.enesincekara.dreamshops.mapper.ProductMapper;
import com.enesincekara.dreamshops.model.Product;
import com.enesincekara.dreamshops.request.AddProductRequest;
import com.enesincekara.dreamshops.request.StockUpdateRequest;
import com.enesincekara.dreamshops.response.products.PageResponse;
import com.enesincekara.dreamshops.response.products.ProductResponse;
import com.enesincekara.dreamshops.service.product.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ProductResponse createProduct(
           @Valid @RequestBody AddProductRequest req
            ){
        Product product = productService.addProduct(req);
        return ProductMapper.toResponse(product);
    }

    @GetMapping
    public PageResponse<ProductResponse> getProducts(
            @PageableDefault(page = 0, size = 10,sort = "name") Pageable pageable
    ) {
        return productService.getAllProducts(pageable);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public PageResponse<ProductResponse> searchProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(page = 0, size = 10,sort = "price") Pageable pageable

    ) {
        return productService.searchProducts(brand, category, active, inStock, minPrice, maxPrice,pageable);
    }

    @PatchMapping("/{id}/stock/increase")
    public void increaseStock(
            @Valid
            @PathVariable Long id,
           @RequestBody StockUpdateRequest req
    ){
        productService.increaseProductStock(id, req.quantity());
    }

    @PatchMapping("/{id}/stock/decrease")
    public void decreaseStock(
            @Valid
            @PathVariable Long id,
            @RequestBody StockUpdateRequest req
            ){
        productService.decreaseProductStock(id, req.quantity());
    }
}
