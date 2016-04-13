package com.sixmac.dao;

import com.sixmac.entity.Ordersinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface OrdersinfoDao extends JpaRepository<Ordersinfo, Integer>, JpaSpecificationExecutor<Ordersinfo> {

    @Query("select a from Ordersinfo a where a.order.id = ?1")
    public List<Ordersinfo> findListByOrderId(Integer orderId);

    @Query("select a from Ordersinfo a where a.product.id = ?1 and a.star <> 0 and a.order.type = ?2 order by a.id desc")
    public List<Ordersinfo> findListBySourceId(Integer productId, Integer type);
}