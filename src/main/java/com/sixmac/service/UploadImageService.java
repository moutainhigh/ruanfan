package com.sixmac.service;


import com.sixmac.entity.Image;
import com.sixmac.entity.vo.FileBo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by tuyh on 2014/12/7.
 */
public interface UploadImageService {

    public FileBo saveImage(MultipartFile file);

    public Image uploadImage(MultipartFile file, String... thumbSizes);

    public String uploadFile(MultipartFile file);

    public List<Image> uploadImages(MultipartFile[] file);

}
