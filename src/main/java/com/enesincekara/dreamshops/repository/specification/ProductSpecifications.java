package com.enesincekara.dreamshops.repository.specification;

import com.enesincekara.dreamshops.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> notDeleted() {
        return (root, query, cb) ->
                cb.isFalse(root.get("deleted"));
    }

    public static Specification<Product> hasBrand(String brand) {

        return (root, query, cb) ->
                brand == null ? null :
                        cb.equal(root.get("brand"), brand);
    }

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) ->
                category == null ? null :
                        cb.equal(root.get("category").get("name"), category);
    }

    public static Specification<Product> isActive(Boolean active) {
        return (root, query, cb) ->
                active == null ? null :
                        cb.equal(root.get("active"), active);
    }

    public static Specification<Product> inStock(Boolean inStock) {
        return (root, query, cb) ->
                inStock == null ? null :
                        inStock
                                ? cb.greaterThan(root.get("inventory"), 0)
                                : cb.equal(root.get("inventory"), 0);
    }

    public static Specification<Product> priceBetween(
            BigDecimal minPrice, BigDecimal maxPrice
    ){
        return (root, query, cb) ->
        {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get("price"), minPrice, maxPrice);
            }
            if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
