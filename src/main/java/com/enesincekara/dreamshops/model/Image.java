package com.enesincekara.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kullanıcıya görünen orijinal isim
    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String contentType;

    // MinIO object name (uuid + originalName)
    @Column(nullable = false, unique = true, updatable = false)
    private String objectKey;

    // API'den indirilecek url
    @Column(nullable = false)
    private String downloadUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public static Image create(String originalFileName, String contentType, String objectKey, String downloadUrl) {
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("originalFileName can't be null or blank");
        }
        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException("contentType can't be null or blank");
        }
        if (objectKey == null || objectKey.isBlank()) {
            throw new IllegalArgumentException("objectKey can't be null or blank");
        }
        if (downloadUrl == null || downloadUrl.isBlank()) {
            throw new IllegalArgumentException("downloadUrl can't be null or blank");
        }

        Image image = new Image();
        image.originalFileName = originalFileName;
        image.contentType = contentType;
        image.objectKey = objectKey;
        image.downloadUrl = downloadUrl;
        return image;
    }

    // ilişki yönetimi için package-private bırakabilirsin
    void setProduct(Product product) {
        this.product = product;
    }
}
