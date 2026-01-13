package com.enesincekara.dreamshops.mapper;

import com.enesincekara.dreamshops.model.Image;
import com.enesincekara.dreamshops.response.image.ImageResponse;

public class ImageMapper {

    public static ImageResponse toResponse(Image image) {
        return new ImageResponse(
                image.getId(),
                image.getOriginalFileName(),
                image.getDownloadUrl()
        );

    }
}
