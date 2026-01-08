package com.enesincekara.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
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
        product.category = category;
        return product;
    }



    public void changedName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("name can't be null or empty");
        }
        this.name = newName;
    }

    public void changedBrand(String newBrand) {
        if (newBrand == null || newBrand.isBlank()) {
            throw new IllegalArgumentException("brand can't be null or empty");
        }
        this.brand = newBrand;
    }
    public void changedPrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price can't be negative");
        }
        this.price = newPrice;
    }

    public void changedInventory(int newInventory) {
        if (newInventory < 0) {
            throw new IllegalArgumentException("inventory can't be negative");
        }
        this.inventory = newInventory;
    }

    public boolean isStock() {
        return inventory > 0;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity can't be negative");
        }
        if (this.inventory < quantity) {
            throw  new IllegalArgumentException("inventory can't be smaller than the quantity ");
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
