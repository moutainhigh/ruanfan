package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.OrdersinfoDao;
import com.sixmac.entity.Ordersinfo;
import com.sixmac.service.OrdersinfoService;
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
    public Page<Ordersinfo> page(final String mobile, final String productName, final String orderNum, int pageNum, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Ordersinfo> page = ordersinfoDao.findAll(new Specification<Ordersinfo>() {
            @Override
            public Predicate toPredicate(Root<Ordersinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("order").get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(productName)) {
                    Predicate pre = cb.like(root.get("productName").as(String.class), "%" + productName + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(orderNum)) {
                    Predicate pre = cb.like(root.get("order").get("orderNum").as(String.class), "%" + orderNum + "%");
                    predicateList.add(pre);
                }

                // 没有评价的订单详情，不会被查出
                Predicate pre1 = cb.isNotNull(root.get("star").as(Integer.class));
                predicateList.add(pre1);

                Predicate pre2 = cb.isNotNull(root.get("comment").as(String.class));
                predicateList.add(pre2);

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
    public Page<Ordersinfo> page(final Integer merchantId, final String mobile, final String productName, int pagenum, int pagesize) {
        PageRequest pageRequest = new PageRequest(pagenum - 1, pagesize, Sort.Direction.ASC, "id");

        Page<Ordersinfo> page = ordersinfoDao.findAll(new Specification<Ordersinfo>() {
            @Override
            public Predicate toPredicate(Root<Ordersinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (merchantId != null) {
                    Predicate pre = cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(mobile)) {
                    Predicate pre = cb.like(root.get("order").get("user").get("mobile").as(String.class), "%" + mobile + "%");
                    predicateList.add(pre);
                }
                if (StringUtils.isNotBlank(productName)) {
                    Predicate pre = cb.like(root.get("productName").as(String.class), "%" + productName + "%");
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
    public List<Ordersinfo> findListByOrderId(Integer orderId) {
        return ordersinfoDao.findListByOrderId(orderId);
    }

    @Override
    public List<Ordersinfo> findListBySourceId(Integer productId, Integer type) {
        return ordersinfoDao.findListBySourceId(productId, type);
    }

    @Override
    public void deleteInfo(Integer orderInfoId) {
        Ordersinfo ordersinfo = ordersinfoDao.findOne(orderInfoId);

        ordersinfo.setStar(null);
        ordersinfo.setComment(null);

        ordersinfoDao.save(ordersinfo);
    }

    @Override
    @Transactional
    public void batchDeleteInfo(int[] ids) {
        for (int id : ids) {
            deleteInfo(id);
        }
    }

}