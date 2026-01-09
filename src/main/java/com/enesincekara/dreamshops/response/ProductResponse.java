package com.enesincekara.dreamshops.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        boolean active,
        boolean inStock,
        boolean lowStock,
        String description,
        String categoryName,
        List<String> imageUrls
) {
}
