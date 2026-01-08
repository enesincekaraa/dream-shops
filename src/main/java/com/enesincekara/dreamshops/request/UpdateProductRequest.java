package com.enesincekara.dreamshops.request;

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
