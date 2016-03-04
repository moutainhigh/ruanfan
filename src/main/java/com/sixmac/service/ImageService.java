package com.sixmac.service;


import com.sixmac.entity.Image;

/**
 * Created by wangbin on 2014/12/9.
 */
public interface ImageService {

    public Image getById(int id);

    public Image deleteById(int id);

    public Image create(Image image);

}
