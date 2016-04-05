package com.sixmac.service.impl;

import com.sixmac.dao.OrdersDao;
import com.sixmac.entity.Orders;
import com.sixmac.service.IncomService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
@Service
public class IncomeServiceImpl implements IncomService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> findAll() {
        return null;
    }

    @Override
    public Page<Orders> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<Orders> find(int pageNum) {
        return null;
    }

    @Override
    public Orders getById(int id) {
        return null;
    }

    @Override
    public Orders deleteById(int id) {
        return null;
    }

    @Override
    public Orders create(Orders orders) {
        return null;
    }

    @Override
    public Orders update(Orders orders) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public Page<Orders> page(String orderNum, String mobile, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");
        Page<Orders> page = ordersDao.findAll(new Specification<Orders>() {

            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(orderNum)) {
                    Predicate pre = cb.like(root.get("orderNum").as(String.class),"%" + orderNum + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }
        }, pageRequest);
        return page;
    }
}
