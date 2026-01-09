package com.enesincekara.dreamshops.controller;

import com.enesincekara.dreamshops.request.StockUpdateRequest;
import com.enesincekara.dreamshops.response.ProductResponse;
import com.enesincekara.dreamshops.service.product.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Products", description = "Product management endpoints")

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
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
