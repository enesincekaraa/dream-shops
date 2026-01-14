package com.enesincekara.dreamshops.controller.categories;


import com.enesincekara.dreamshops.mapper.CategoryMapper;
import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.request.AddCategoryRequest;
import com.enesincekara.dreamshops.response.categories.CategoryResponse;
import com.enesincekara.dreamshops.service.category.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody AddCategoryRequest req) {
        Category category =  categoryService.addCategory(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryMapper.toResponse(category));
    }
}
