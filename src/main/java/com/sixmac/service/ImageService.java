package com.sixmac.service;


import com.sixmac.entity.Image;

import java.util.List;

/**
 * Created by wangbin on 2014/12/9.
 */
public interface ImageService {

    public Image getById(int id);

    public Image deleteById(int id);

    public Image create(Image image);

    public Image update(Image image);

    // 根据图片所属目标id和图片所属目标类型查询列表
    public List<Image> iFindList(Integer objectId, Integer objectType);

    // 根据图片所属目标id和图片所属目标类型删除相关联的图片信息
    public void deleteInfo(Integer objectId, Integer objectType);
}
