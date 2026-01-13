package com.enesincekara.dreamshops.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {
    String upload(MultipartFile file) throws IOException;
    byte[] download(String objectKey);
    void delete(String objectKey);
}
