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

    public Page<Ordersinfo> page(Integer merchantId, String mobile, String productName, int pagenum, int pagesize);

    // 根据订单id查询订单详情列表
    public List<Ordersinfo> findListByOrderId(Integer orderId);

    // 根据目标id和订单类型查询订单详情列表
    public List<Ordersinfo> findListBySourceId(Integer productId, Integer type);

    // 根据订单详情id删除评价信息
    public void deleteInfo(Integer orderInfoId);

    // 批量根据订单详情id删除评价信息
    public void batchDeleteInfo(int[] ids);
}