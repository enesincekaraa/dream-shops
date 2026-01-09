package com.enesincekara.dreamshops.repository;

import com.enesincekara.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByProductIdAndFileName(Long productId,String fileName);
}
