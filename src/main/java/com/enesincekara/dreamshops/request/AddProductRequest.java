package com.enesincekara.dreamshops.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record AddProductRequest(
         @NotBlank(message = "Product name is required")
         String name,
         @NotBlank(message = "Brand is required")
         String brand,
         @NotNull(message = "Price is required")
         @Positive(message = "Price must be greater than zero")
         BigDecimal price,
         @PositiveOrZero(message = "Inventory cannot be negative")
         int inventory,
         String description,
         @NotBlank(message = "Category name is required")
         String categoryName
) {
}
