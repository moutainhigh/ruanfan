package com.sixmac.service;

import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.common.ICommonService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 1:39.
 */
public interface OrdersinfoService extends ICommonService<Ordersinfo> {

    // 根据订单id查询订单详情list
    public List<Ordersinfo> findListByOrderId(Integer orderId);
}