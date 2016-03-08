package com.sixmac.service;

import com.sixmac.entity.Orders;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:55.
 */
public interface OrdersService extends ICommonService<Orders> {

    // 订单列表
    public Page<Orders> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据订单流水号获取订单详情
    public Orders iFindOneByOrderNum(String orderNum);
}