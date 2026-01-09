package com.enesincekara.dreamshops.request;



import java.math.BigDecimal;

public record AddProductRequest(
         String name,
         String brand,
         BigDecimal price,
         int inventory,
         String description,
         String categoryName
) {
}
