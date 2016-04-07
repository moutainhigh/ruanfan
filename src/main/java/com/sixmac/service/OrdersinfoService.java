package com.sixmac.service;

import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 1:39.
 */
public interface OrdersinfoService extends ICommonService<Ordersinfo> {

    // 评价列表（分页）
    public Page<Ordersinfo> page(String mobile, String productName, String orderNum, int pagenum, int pagesize);

    // 根据订单id查询订单详情列表
    public List<Ordersinfo> findListByOrderId(Integer orderId);

    // 根据商品id查询订单详情列表
    public List<Ordersinfo> findListByProductId(Integer productId);
}