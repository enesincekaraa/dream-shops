package com.enesincekara.dreamshops.model;


import com.enesincekara.dreamshops.repository.CategoryRepository;
import com.enesincekara.dreamshops.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("dev")
class ProductOptimisticLockTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ProductRepository productRepository;

    @Test
    void shouldThrowOptimisticLockExceptionOnConcurrentUpdate() {

        // --- setup ---
        EntityManager emSetup = emf.createEntityManager();
        emSetup.getTransaction().begin();

        Category category = Category.create("Electronics");
        emSetup.persist(category);

        Product product = Product.create(
                "MacBook",
                "Apple",
                BigDecimal.valueOf(50000),
                5,
                "desc",
                category
        );
        emSetup.persist(product);
        emSetup.getTransaction().commit();
        emSetup.close();

        // --- TX1 ---
        EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();
        Product p1 = em1.find(Product.class, product.getId());

        // --- TX2 ---
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Product p2 = em2.find(Product.class, product.getId());

        // TX1 update
        p1.decreaseStock(1);
        em1.flush();
        em1.getTransaction().commit();
        em1.close();

        // TX2 update (STALE!)
        assertThrows(OptimisticLockException.class, () -> {
            p2.decreaseStock(1);
            em2.flush(); // 💥 BURADA PATLAR
        });

        em2.getTransaction().rollback();
        em2.close();
    }
}
