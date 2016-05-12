package com.sixmac.utils;

import com.sixmac.entity.Image;
import com.sixmac.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetImageInfo extends Thread {

    private Image image;

    private String key;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void run() {
        try {
            ImageService imageService = (ImageService) BeanUtil.getBean("imageServiceImpl");

            Map<String, Object> map = QiNiuUploadImgUtil.getInfo(key);
            image.setWidth(map.get("width").toString());
            image.setHeight(map.get("height").toString());

            imageService.update(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
