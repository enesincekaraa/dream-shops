package com.enesincekara.dreamshops.service.category;

import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.request.AddCategoryRequest;

public interface ICategoryService {
    Category addCategory(AddCategoryRequest req);
}
