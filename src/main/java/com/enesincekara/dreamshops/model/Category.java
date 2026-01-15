package com.enesincekara.dreamshops.model;

import jakarta.persistence.OneToMany;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    public static Category create(
            String name
    ){

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        Category category = new Category();
        category.name = name;
        return category;
    }
}
