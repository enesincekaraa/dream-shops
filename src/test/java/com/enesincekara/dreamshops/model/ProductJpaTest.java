package com.enesincekara.dreamshops.model;

import com.enesincekara.dreamshops.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("dev")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductJpaTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    void shouldPersistProduct(){
        Category category = Category.create("Electronics");
        entityManager.persistAndFlush(category);

        Product product = Product.create(
                "MacBook",
                "Apple",
                BigDecimal.valueOf(50000),
                5,
                "desc",
                category
        );
        Product saved= productRepository.saveAndFlush(product);

        assertNotNull(saved.getId());
        assertNotNull(saved.getSku());
        assertEquals("Electronics",saved.getCategory().getName());
    }
}
