package com.sixmac.service;

import com.sixmac.entity.Products;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:37.
 */
public interface ProductsService extends ICommonService<Products> {

    // 商品列表
    public Page<Products> iPage(Integer type, String name, Integer brandId, Integer sortId, Integer isHot, Integer pageNum, Integer pageSize);
}