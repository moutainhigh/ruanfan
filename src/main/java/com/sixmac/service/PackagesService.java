package com.sixmac.service;

import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Packages;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:12.
 */
public interface PackagesService extends ICommonService<Packages> {

    // 套餐列表
    public Page<Packages> iPage(Integer brandId, Integer pageNum, Integer pageSize);

    // 根据套餐id查询对应的商品列表
    public List<Packageproducts> findListByPackageId(Integer packageId);

    // 套餐列表
    public Page<Packages> page(String name, Integer brandId, Integer pageNum, Integer pageSize);

}