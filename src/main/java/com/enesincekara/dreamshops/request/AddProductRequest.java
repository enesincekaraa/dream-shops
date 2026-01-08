package com.enesincekara.dreamshops.request;

import com.enesincekara.dreamshops.model.Category;


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
