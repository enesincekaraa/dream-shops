package com.enesincekara.dreamshops.mapper;

import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.response.CategoryResponse;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getProducts());
    }
}
