package com.sixmac.service;

import com.sixmac.entity.Image;
import com.sixmac.entity.Packageproducts;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021 下午 2:27.
 */
public interface PackageproductsService extends ICommonService<Packageproducts> {

    // 根据商品套餐id查询对应的套餐关联详情
    public List<Packageproducts> findListByPackageId(Integer packageId, Integer type);

    // 根据商品套餐id查询对应的套餐关联详情的图片集合
    public List<Image> findImageListByPackageId(Integer packageId, Integer type);

    // 根据商品套餐id删除对应的套餐关联信息
    public void deleteByPackageId(Integer packageId);
}