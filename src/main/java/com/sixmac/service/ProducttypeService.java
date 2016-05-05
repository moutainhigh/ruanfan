package com.sixmac.service;

import com.sixmac.entity.Products;
import com.sixmac.entity.Producttype;
import com.sixmac.service.common.ICommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015 下午 3:44.
 */
public interface ProducttypeService extends ICommonService<Producttype> {

    // 根据商品分类id查询对应的商品信息集合
    public List<Products> findProductListByProductTypeId(Integer productTypeId);

    public void deleteById(HttpServletRequest request, Integer id);
}