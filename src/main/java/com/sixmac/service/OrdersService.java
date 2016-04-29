package com.sixmac.service;

import com.sixmac.entity.Orders;
import com.sixmac.service.common.ICommonService;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:55.
 */
public interface OrdersService extends ICommonService<Orders> {

    // 订单列表
    public Page<Orders> iPage(Integer userId, Integer pageNum, Integer pageSize);

    // 根据订单流水号获取订单详情
    public Orders iFindOneByOrderNum(String orderNum);

    public Page<Orders> page(String orderNum, String mobile, String nickName, Integer status, Integer type, int pageNum, int pageSize);

    public Page<Orders> page(Integer merchantId, String orderNum, String mobile, String productName, Integer status, Integer type, int pageNum, int pageSize);

    public Page<Orders> page(Integer merchantId, String orderNum, String mobile, String productName, Integer status, Date startTime, Date endTime, int pageNum, int pageSize);

    // 根据订单状态查询不同状态的订单数量
    public List<Orders> findListByStatus(Integer status);

    // 查询新增订单列表
    public List<Orders> findListNew();

    // 根据订单流水号获取微信支付信息
    public Map<String, Object> getPayInfo(HttpServletRequest request, HttpServletResponse response, String orderNum);
}