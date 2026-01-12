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
    private String fileName;
    private String fileType;

    @Column(nullable = false)
    private String url;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private Image(
            String fileName,
            String fileType,
            String url
    ){
        this.fileName = fileName;
        this.fileType = fileType;
        this.url = url;
    }

    public static Image create(String fileName, String fileType, String url){
        if(fileName == null || fileName.isBlank()){
            throw new IllegalArgumentException("Invalid file name");
        }
        if(url == null || url.isBlank()){
            throw new IllegalArgumentException("Invalid file type");
        }
        return new Image(fileName, fileType, url);
    }
    void assignProduct(Product product){
        this.product = product;
    }
}
