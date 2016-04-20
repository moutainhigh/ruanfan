package com.sixmac.service;

import com.sixmac.entity.Brand;
import com.sixmac.entity.Packages;
import com.sixmac.entity.Products;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015 下午 5:55.
 */
public interface BrandService extends ICommonService<Brand> {

    // 根据商品品牌id查询对应的套餐信息集合
    public List<Packages> findPackageListByBrandId(Integer brandId);
}