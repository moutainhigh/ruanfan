package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ProductsDao;
import com.sixmac.entity.Products;
import com.sixmac.service.ProductsService;
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
 * Created by Administrator on 2016/3/8 0008 上午 10:37.
 */
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsDao productsDao;

    @Override
    public List<Products> findAll() {
        return productsDao.findAll();
    }

    @Override
    public Page<Products> find(int pageNum, int pageSize) {
        return productsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Products> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Products getById(int id) {
        return productsDao.findOne(id);
    }

    @Override
    public Products deleteById(int id) {
        Products products = getById(id);
        productsDao.delete(products);
        return products;
    }

    @Override
    public Products create(Products products) {
        return productsDao.save(products);
    }

    @Override
    public Products update(Products products) {
        return productsDao.save(products);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Products> iPage(Integer type, String name, Integer brandId, Integer sortId, Integer isHot, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Products> page = productsDao.findAll(new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }
                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (brandId != null) {
                    Predicate pre = cb.equal(root.get("brand").get("id").as(Integer.class), brandId);
                    predicateList.add(pre);
                }
                if (sortId != null) {
                    Predicate pre = cb.equal(root.get("sort").get("id").as(Integer.class), sortId);
                    predicateList.add(pre);
                }
                if (isHot != null) {
                    Predicate pre = cb.equal(root.get("isHot").as(Integer.class), isHot);
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