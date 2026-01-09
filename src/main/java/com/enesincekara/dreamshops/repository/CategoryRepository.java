package com.enesincekara.dreamshops.repository;

import com.enesincekara.dreamshops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String categoryName);
    Optional<Category> findByName(String categoryName);
}
