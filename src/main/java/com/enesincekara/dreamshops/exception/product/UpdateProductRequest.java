package com.enesincekara.dreamshops.exception.product;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String brand,
        BigDecimal price,
        Integer inventory,
        String description,
        String categoryName
) {
}
