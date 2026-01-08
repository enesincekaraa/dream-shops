package com.enesincekara.dreamshops.model;

import com.enesincekara.dreamshops.exception.product.InsufficientStockException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    @Column(nullable = false, unique = true, updatable = false)
    private String sku;
    private boolean active = true;
    private boolean deleted = false;
    @Column(nullable = false)
    private int lowStockThreshold = 5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();


    public static Product create(
            String name,
            String brand,
            BigDecimal price,
            int inventory,
            String description,
            Category category
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name can't be null or empty");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("brand can't be null or empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price can't be negative");
        }
        if (inventory < 0 ) {
            throw new IllegalArgumentException("inventory can't be negative");
        }
        if (category == null) {
            throw new IllegalArgumentException("category can't be null");
        }
        Product product = new Product();
        product.name = name;
        product.brand = brand;
        product.price = price;
        product.inventory = inventory;
        product.description = description;
        product.sku = UUID.randomUUID().toString();
        product.category = category;
        return product;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }


    public void delete() {
        this.deleted = true;
        this.active = false;
    }


    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("name can't be null or empty");
        }
        this.name = newName;
    }

    public void changeBrand(String newBrand) {
        if (newBrand == null || newBrand.isBlank()) {
            throw new IllegalArgumentException("brand can't be null or empty");
        }
        this.brand = newBrand;
    }
    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }
    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price can't be negative");
        }
        this.price = newPrice;
    }

    public void changeInventory(int newInventory) {
        if (newInventory < 0) {
            throw new IllegalArgumentException("inventory can't be negative");
        }
        this.inventory = newInventory;
    }

    public void changeCategory(Category category ) {
        if (category == null) {
            throw new IllegalArgumentException("category name can't be null");
        }
        this.category = category;
    }

    public boolean isInStock() {
        return inventory > 0;
    }

    public boolean isLowStock() {
        return inventory <= lowStockThreshold;
    }
    public void  increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        this.inventory += quantity;
    }
    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (this.inventory < quantity) {
            throw new InsufficientStockException("Not enough stock. Current stock: " + inventory);
        }
        this.inventory -= quantity;
    }


    public void addImage(Image image) {
        images.add(image);
        image.setProduct(this);
    }
    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }





}
