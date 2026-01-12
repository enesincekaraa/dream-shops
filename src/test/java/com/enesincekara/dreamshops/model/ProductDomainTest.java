package com.enesincekara.dreamshops.model;


import com.enesincekara.dreamshops.exception.product.InsufficientStockException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class ProductDomainTest {

    @Test
    void shouldDecreaseStock() {

        Category category= Category.create("Electronics");

        Product product = Product.create(
                "MacBook",
                "Apple",
                BigDecimal.valueOf(50000),
                5,
                "desc",
                category
        );

        product.decreaseStock(2);

        log.info(String.valueOf(product.getInventory()));

        assertEquals(3,product.getInventory());

    }

    @Test
    void shouldThrowExceptionWhenStockInsufficient() {
        Category category= Category.create("Electronics");
        Product product = Product.create(
                "MacBook",
                "Apple",
                BigDecimal.valueOf(50000),
                1,
                "desc",
                category
        );

        log.info(String.valueOf(product.getInventory()));

        assertThrows(InsufficientStockException.class,
                () -> product.decreaseStock(2));
    }





}
