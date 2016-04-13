package com.sixmac.dao;

import com.sixmac.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface OrdersDao extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    @Query("select a from Orders a where a.orderNum = ?1")
    public Orders iFindOneByOrderNum(String orderNum);

    @Query("select a from Orders a where a.status = ?1")
    public List<Orders> findListByStatus(Integer status);

    @Query("select a from Orders a where a.createTime > ?1")
    public List<Orders> findListNew(Date oldDate);

    @Query("select a from Orders a where a.orderNum like ?2 and a.mobile like ?3 and a.id in (select b from Ordersinfo b where b.productName like ?4) and a.status = ?5 and a.merchant.id = ?1 and a.createTime between ?6 and ?7")
    public Page<Orders> pageByParams(Integer merchantId, String orderNum, String mobile, String productName, Integer status, Date startTime, Date endTime, Pageable pageable);
}