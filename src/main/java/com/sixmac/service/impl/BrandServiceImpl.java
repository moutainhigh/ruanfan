package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.BrandDao;
import com.sixmac.entity.Brand;
import com.sixmac.entity.Packages;
import com.sixmac.entity.Products;
import com.sixmac.service.BrandService;
import com.sixmac.service.OperatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015 下午 5:56.
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private OperatisService operatisService;

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    public Page<Brand> find(int pageNum, int pageSize) {
        return brandDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Brand> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Brand getById(int id) {
        return brandDao.findOne(id);
    }

    @Override
    public Brand deleteById(int id) {
        Brand brand = getById(id);
        brandDao.delete(brand);
        return brand;
    }

    @Override
    public Brand create(Brand brand) {
        return brandDao.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        return brandDao.save(brand);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Packages> findPackageListByBrandId(Integer brandId) {
        return brandDao.findPackageListByBrandId(brandId);
    }

    @Override
    public void deleteById(HttpServletRequest request, Integer id) {
        Brand brand = getById(id);

        operatisService.addOperatisInfo(request, "删除品牌分类 " + brand.getName());

        brandDao.delete(brand);
    }
}