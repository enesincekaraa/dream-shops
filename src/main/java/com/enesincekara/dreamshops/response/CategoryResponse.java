package com.enesincekara.dreamshops.response;

import com.enesincekara.dreamshops.model.Product;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<Product> products
) {
}
