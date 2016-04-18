package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.OrdersDao;
import com.sixmac.entity.Orders;
import com.sixmac.service.OrdersService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public Page<Orders> iPage(Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Orders> page = ordersDao.findAll(new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (userId != null) {
                    Predicate pre = cb.equal(root.get("user").get("id").as(Integer.class), userId);
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

    @Override
    public Orders iFindOneByOrderNum(String orderNum) {
        return ordersDao.iFindOneByOrderNum(orderNum);
    }

    @Override
    public Page<Orders> page(String orderNum, String mobile, String nickName, Integer status, Integer type, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Orders> page = ordersDao.findAll(new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(orderNum)) {
                    Predicate pre = cb.like(root.get("orderNum").as(String.class), "%" + orderNum + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(nickName)) {
                    Predicate pre = cb.like(root.get("merchant").get("nickName").as(String.class), "%" + nickName + "%");
                    predicateList.add(pre);
                }
                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
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

    @Override
    public Page<Orders> page(Integer merchantId, String orderNum, String mobile, String productName, Integer status, Integer type, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Orders> page = ordersDao.findAll(new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (merchantId != null) {
                    Predicate pre = cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(orderNum)) {
                    Predicate pre = cb.like(root.get("orderNum").as(String.class), "%" + orderNum + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(productName)) {
                    Predicate pre = cb.like(root.get("ordersinfo").get("productName").as(String.class), "%" + productName + "%");
                    predicateList.add(pre);
                }
                if (status != null) {
                    Predicate pre = cb.equal(root.get("status").as(Integer.class), status);
                    predicateList.add(pre);
                }
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
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

    @Override
    public Page<Orders> page(Integer merchantId, String orderNum, String mobile, String productName, Integer status, Date startTime, Date endTime, int pageNum, int pageSize) {
        orderNum = "%" + orderNum + "%";
        mobile = "%" + mobile + "%";
        productName = "%" + productName + "%";

        return ordersDao.pageByParams(merchantId, orderNum, mobile, productName, status, startTime, endTime, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public List<Orders> findListByStatus(Integer status) {
        return ordersDao.findListByStatus(status);
    }

    @Override
    public List<Orders> findListNew() {
        return ordersDao.findListNew(CommonController.getOldDate());
    }
}