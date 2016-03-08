package com.sixmac.dao;

import com.sixmac.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface OrdersDao extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    @Query("select a from Orders a where a.orderNum = ?1")
    public Orders iFindOneByOrderNum(String orderNum);
}