package com.enesincekara.dreamshops.service.image;

import com.enesincekara.dreamshops.exception.image.ImageStorageException;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioImageStorageService implements ImageStorageService {

    private final MinioClient minioClient;
    private final String bucket;

    public MinioImageStorageService(
            MinioClient minioClient,
            @Value("${minio.bucket}") String bucket
    ) {
        this.minioClient = minioClient;
        this.bucket = bucket;
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            ensureBucketExists();

            String objectKey = UUID.randomUUID() + "-" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );

            return objectKey;
        } catch (Exception e) {
            throw new ImageStorageException("Failed to upload image to MinIO", e);
        }
    }

    @Override
    public byte[] download(String objectKey) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .build()
            );

            // stream -> byte[]
            try (stream) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                stream.transferTo(baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new ImageStorageException("Failed to download image from MinIO", e);
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new ImageStorageException("Failed to delete image from MinIO", e);
        }
    }

    private void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucket).build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucket).build()
                );
            }
        } catch (Exception e) {
            throw new ImageStorageException("Failed to ensure bucket exists: " + bucket, e);
        }
    }
}
