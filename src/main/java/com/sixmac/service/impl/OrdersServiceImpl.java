package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.OrdersDao;
import com.sixmac.entity.Orders;
import com.sixmac.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:56.
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> findAll() {
        return ordersDao.findAll();
    }

    @Override
    public Page<Orders> find(int pageNum, int pageSize) {
        return ordersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Orders> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Orders getById(int id) {
        return ordersDao.findOne(id);
    }

    @Override
    public Orders deleteById(int id) {
        Orders orders = getById(id);
        ordersDao.delete(orders);
        return orders;
    }

    @Override
    public Orders create(Orders orders) {
        return ordersDao.save(orders);
    }

    @Override
    public Orders update(Orders orders) {
        return ordersDao.save(orders);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}