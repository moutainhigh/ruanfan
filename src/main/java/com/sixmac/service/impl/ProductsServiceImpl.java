package com.sixmac.service.impl;

import com.sixmac.controller.common.CommonController;
import com.sixmac.core.Constant;
import com.sixmac.dao.ImageDao;
import com.sixmac.dao.MessageplusDao;
import com.sixmac.dao.ProductsDao;
import com.sixmac.entity.Messageplus;
import com.sixmac.entity.Products;
import com.sixmac.service.ProductsService;
import com.sixmac.utils.PathUtils;
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
 * Created by Administrator on 2016/3/8 0008 上午 10:37.
 */
@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsDao productsDao;

    @Autowired
    private MessageplusDao messageplusDao;

    @Autowired
    private ImageDao imageDao;

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
        products.setStatus(Constant.BANNED_STATUS_NO);
        products.setIsAdd(Constant.ADDED_STATUS_NO);
        productsDao.save(products);
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
    public Page<Products> iPage(Integer type, String name, Integer merchantId, Integer brandId, Integer sortId, Integer isHot, Integer pageNum, Integer pageSize) {
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
                if (StringUtils.isNotBlank(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }
                if (merchantId != null) {
                    Predicate pre = cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
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

                // 已上架的商品才可以被查出来
                Predicate pre1 = cb.equal(root.get("isAdd").as(Integer.class), Constant.ADDED_STATUS_YES);
                predicateList.add(pre1);

                // 审核通过的商品才可以被查出来
                Predicate pre2 = cb.equal(root.get("isCheck").as(Integer.class), Constant.CHECK_STATUS_SUCCESS);
                predicateList.add(pre2);

                // 没有禁用的商品才可以被查出来
                Predicate pre3 = cb.equal(root.get("status").as(Integer.class), Constant.BANNED_STATUS_YES);
                predicateList.add(pre3);

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
    public Page<Products> page(String name, String merchantName, Integer isCheck, Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Products> page = productsDao.findAll(new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (merchantName != null) {
                    Predicate pre = cb.like(root.get("merchant").get("nickName").as(String.class), "%" + merchantName + "%");
                    predicateList.add(pre);
                }

                if (isCheck != null) {
                    Predicate pre = cb.equal(root.get("isCheck").as(Integer.class), isCheck);
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.equal(root.get("status").as(Integer.class), Constant.BANNED_STATUS_YES);
                predicateList.add(pre1);

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
    @Transactional
    public void changeCheck(Integer productId, Integer isCheck, String reason) {
        Products products = productsDao.findOne(productId);
        products.setIsCheck(isCheck);

        // 修改审核状态
        productsDao.save(products);

        // 添加系统消息
        Messageplus message = new Messageplus();
        message.setTitle("系统消息");
        message.setSourceId(products.getMerchant().getId());
        message.setType(Constant.MESSAGE_PLUS_MERCHANTS);
        message.setDescription("发布的商品 " + products.getName() + " 审核" + (isCheck == 1 ? "通过" : "不通过，驳回原因：" + reason));
        message.setCreateTime(new Date());

        messageplusDao.save(message);
    }

    @Override
    public Page<Products> page(Integer merchantId, String name, Integer isCheck, Integer type, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Products> page = productsDao.findAll(new Specification<Products>() {
            @Override
            public Predicate toPredicate(Root<Products> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (merchantId != null) {
                    Predicate pre = cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
                    predicateList.add(pre);
                }

                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (isCheck != null) {
                    Predicate pre = cb.equal(root.get("isCheck").as(Integer.class), isCheck);
                    predicateList.add(pre);
                }

                if (type != null) {
                    Predicate pre = cb.equal(root.get("type").as(Integer.class), type);
                    predicateList.add(pre);
                }

                Predicate pre1 = cb.equal(root.get("status").as(Integer.class), Constant.BANNED_STATUS_YES);
                predicateList.add(pre1);

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
    public List<Products> iFindList() {
        List<Products> list = new ArrayList<Products>();
        List<Products> oneList = productsDao.iFindList(Constant.PRODUCT_TYPE_ONE);
        if (null != oneList && oneList.size() > 0) {
            list.add(oneList.get(0));
        }

        List<Products> twoList = productsDao.iFindList(Constant.PRODUCT_TYPE_TWO);
        if (null != twoList && twoList.size() > 0) {
            list.add(twoList.get(0));
        }

        List<Products> threeList = productsDao.iFindList(Constant.PRODUCT_TYPE_THREE);
        if (null != threeList && threeList.size() > 0) {
            list.add(threeList.get(0));
        }

        for (Products product : list) {
            product.setCover(PathUtils.getRemotePath() + imageDao.findOne(product.getCoverId()).getPath());
        }

        return list;
    }

    @Override
    public List<Products> iFindListBySortAndStyle(Integer productId, Integer type, Integer sortId) {
        List<Products> list = productsDao.iFindList(type, sortId, productId);

        for (Products product : list) {
            product.setCover(PathUtils.getRemotePath() + imageDao.findOne(product.getCoverId()).getPath());
        }

        return list;
    }

    @Override
    public List<Products> findList() {
        return productsDao.findList();
    }

    @Override
    public List<Products> findListCheck() {
        return productsDao.findListCheck();
    }

    @Override
    public List<Products> findListAdd() {
        return productsDao.findListAdd();
    }

    @Override
    public List<Products> findListDown() {
        return productsDao.findListDown();
    }

    @Override
    public List<Products> findListNew() {
        return productsDao.findListNew(CommonController.getYesterday());
    }
}