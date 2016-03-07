package com.sixmac.service.impl;


import com.sixmac.dao.ImageDao;
import com.sixmac.entity.Image;
import com.sixmac.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wangbin on 14-10-16.
 */
@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image getById(int id) {
        return imageDao.findOne(id);
    }

    @Override
    @Transactional
    public Image deleteById(int id) {
        Image image = getById(id);
        imageDao.delete(image);
        return image;
    }

    @Override
    @Transactional
    public Image create(Image image) {
        image.setCreateTime(new Date());
        return imageDao.save(image);
    }

    @Override
    public List<Image> iFindList(Integer objectId, Integer objectType) {
        return imageDao.iFindList(objectId, objectType);
    }
}
