package com.enesincekara.dreamshops.service.image;


import com.enesincekara.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {
    void uploadImage(Long productId,MultipartFile file) throws IOException;
    Image downloadImage(Long productId,String fileName);
}
