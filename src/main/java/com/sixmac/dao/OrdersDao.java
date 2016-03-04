package com.sixmac.dao;

import com.sixmac.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/3/4 0004 下午 2:45.
 */
public interface OrdersDao extends JpaRepository<Orders, Integer> {

}