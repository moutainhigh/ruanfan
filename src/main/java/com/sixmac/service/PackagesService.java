package com.sixmac.service;

import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Packages;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:12.
 */
public interface PackagesService extends ICommonService<Packages> {

    // 套餐列表
    public Page<Packages> iPage(Integer brandId, String name, Integer pageNum, Integer pageSize);

    // 根据套餐id查询对应的商品列表
    public List<Packageproducts> findListByPackageId(Integer packageId);

    // 套餐列表
    public Page<Packages> page(String name, Integer brandId, Integer pageNum, Integer pageSize);

    // 根据品牌id查询类似的套餐列表
    public List<Packages> iFindListByBrand(Integer packageId, Integer brandId);

    // 根据创建时间倒序排列套餐列表
    public List<Packages> findListOrderByCreateTimeDesc();

    public void deleteById(HttpServletRequest request, Integer id);
}