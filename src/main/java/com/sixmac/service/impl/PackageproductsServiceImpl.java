package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.PackageproductsDao;
import com.sixmac.entity.Packageproducts;
import com.sixmac.service.PackageproductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021 下午 2:28.
 */
@Service
public class PackageproductsServiceImpl implements PackageproductsService {

    @Autowired
    private PackageproductsDao packageproductsDao;

    @Override
    public List<Packageproducts> findAll() {
        return packageproductsDao.findAll();
    }

    @Override
    public Page<Packageproducts> find(int pageNum, int pageSize) {
        return packageproductsDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Packageproducts> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Packageproducts getById(int id) {
        return packageproductsDao.findOne(id);
    }

    @Override
    public Packageproducts deleteById(int id) {
        Packageproducts packageproducts = getById(id);
        packageproductsDao.delete(packageproducts);
        return packageproducts;
    }

    @Override
    public Packageproducts create(Packageproducts packageproducts) {
        return packageproductsDao.save(packageproducts);
    }

    @Override
    public Packageproducts update(Packageproducts packageproducts) {
        return packageproductsDao.save(packageproducts);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Packageproducts> findListByPackageId(Integer packageId) {
        return packageproductsDao.findListByPackageId(packageId);
    }

    @Override
    @Transactional
    public void deleteByPackageId(Integer packageId) {
        List<Packageproducts> list = packageproductsDao.findListByPackageId(packageId);
        for (Packageproducts packageProduct : list) {
            packageproductsDao.delete(packageProduct.getId());
        }
    }
}