package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ProducttypeDao;
import com.sixmac.entity.Products;
import com.sixmac.entity.Producttype;
import com.sixmac.service.OperatisService;
import com.sixmac.service.ProducttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015 下午 3:44.
 */
@Service
public class ProducttypeServiceImpl implements ProducttypeService {

    @Autowired
    private ProducttypeDao producttypeDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Producttype> findAll() {
        return producttypeDao.findAll();
    }

    @Override
    public Page<Producttype> find(int pageNum, int pageSize) {
        return producttypeDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Producttype> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Producttype getById(int id) {
        return producttypeDao.findOne(id);
    }

    @Override
    public Producttype deleteById(int id) {
        Producttype producttype = getById(id);
        producttypeDao.delete(producttype);
        return producttype;
    }

    @Override
    public Producttype create(Producttype producttype) {
        return producttypeDao.save(producttype);
    }

    @Override
    public Producttype update(Producttype producttype) {
        return producttypeDao.save(producttype);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Products> findProductListByProductTypeId(Integer productTypeId) {
        return producttypeDao.findProductListByProductTypeId(productTypeId);
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Producttype producttype = getById(id);

        operatisService.addOperatisInfo(request, "删除商品种类 " + producttype.getName());

        producttypeDao.delete(producttype);
    }
}