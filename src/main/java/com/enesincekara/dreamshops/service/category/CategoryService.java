package com.enesincekara.dreamshops.service.category;

import com.enesincekara.dreamshops.exception.category.CategoryAlreadyExistsException;
import com.enesincekara.dreamshops.model.Category;
import com.enesincekara.dreamshops.repository.CategoryRepository;
import com.enesincekara.dreamshops.request.AddCategoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category  addCategory(AddCategoryRequest req) {
        if(categoryRepository.existsByName(req.name())){
            throw new CategoryAlreadyExistsException("Category with name " + req.name() + " already exists");
        }
        Category category = Category.create(
                req.name()
        );
        return categoryRepository.save(category);
    }
}
