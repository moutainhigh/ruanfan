package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.OrdersinfoDao;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.OrdersinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 下午 1:40.
 */
@Service
public class OrdersinfoServiceImpl implements OrdersinfoService {

    @Autowired
    private OrdersinfoDao ordersinfoDao;

    @Override
    public List<Ordersinfo> findAll() {
        return ordersinfoDao.findAll();
    }

    @Override
    public Page<Ordersinfo> find(int pageNum, int pageSize) {
        return ordersinfoDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Ordersinfo> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Ordersinfo getById(int id) {
        return ordersinfoDao.findOne(id);
    }

    @Override
    public Ordersinfo deleteById(int id) {
        Ordersinfo ordersinfo = getById(id);
        ordersinfoDao.delete(ordersinfo);
        return ordersinfo;
    }

    @Override
    public Ordersinfo create(Ordersinfo ordersinfo) {
        return ordersinfoDao.save(ordersinfo);
    }

    @Override
    public Ordersinfo update(Ordersinfo ordersinfo) {
        return ordersinfoDao.save(ordersinfo);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Ordersinfo> findListByOrderId(Integer orderId) {
        List<Ordersinfo> list = ordersinfoDao.findListByOrderId(orderId);

        for (Ordersinfo orderInfo : list) {
            orderInfo.setProductId(orderInfo.getProduct().getId());
        }

        return list;
    }
}