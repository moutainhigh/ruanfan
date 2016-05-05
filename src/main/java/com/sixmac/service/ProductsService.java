package com.sixmac.service;

import com.sixmac.entity.Products;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 10:37.
 */
public interface ProductsService extends ICommonService<Products> {

    // 商品列表
    public Page<Products> iPage(Integer type, String name, Integer merchantId, Integer brandId, Integer sortId, Integer isHot, Integer pageNum, Integer pageSize);

    // 商品列表
    public Page<Products> page(String name, String merchantName, Integer isCheck, Integer type, Integer pageNum, Integer pageSize);

    // 审核商品
    public void changeCheck(HttpServletRequest request, Integer productId, Integer isCheck, String reason);

    // 商品列表
    public Page<Products> page(Integer merchantId, String name, Integer isCheck, Integer type, Integer pageNum, Integer pageSize);

    // 根据类别和种类id查询类似的商品列表
    public List<Products> iFindListBySortAndStyle(Integer productId, Integer type, Integer sortId);

    // 查询商品总数
    public List<Products> findList();

    // 查询待审核商品
    public List<Products> findListCheck();

    // 查询上架商品数量
    public List<Products> findListAdd();

    // 查询下架商品数量
    public List<Products> findListDown();

    // 查询昨日新增商品
    public List<Products> findListNew();

    // 查询合格的商品列表
    public List<Products> findListWithSuccess();
}