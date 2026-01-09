package com.enesincekara.dreamshops.repository;

import com.enesincekara.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
