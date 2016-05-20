package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.ImageDao;
import com.sixmac.dao.PackageproductsDao;
import com.sixmac.dao.PackagesDao;
import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Packages;
import com.sixmac.service.OperatisService;
import com.sixmac.service.PackagesService;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008 上午 11:12.
 */
@Service
public class PackagesServiceImpl implements PackagesService {

    @Autowired
    private PackagesDao packagesDao;

    @Autowired
    private PackageproductsDao packageproductsDao;

    @Autowired
    private OperatisService operatisService;

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<Packages> findAll() {
        return packagesDao.findAll();
    }

    @Override
    public Page<Packages> find(int pageNum, int pageSize) {
        return packagesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Packages> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SIZE);
    }

    @Override
    public Packages getById(int id) {
        return packagesDao.findOne(id);
    }

    @Override
    @Transactional
    public Packages deleteById(int id) {
        Packages packages = getById(id);

        // 删除商品套餐的同时，删除套餐和商品的关联信息
        List<Packageproducts> list = packageproductsDao.findListByPackageId(id, packages.getType());
        for (Packageproducts packageProduct : list) {
            packageproductsDao.delete(packageProduct.getId());
        }

        packagesDao.delete(packages);
        return packages;
    }

    @Override
    public Packages create(Packages packages) {
        return packagesDao.save(packages);
    }

    @Override
    public Packages update(Packages packages) {
        return packagesDao.save(packages);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Packages> iPage(final Integer brandId, final String name, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Packages> page = packagesDao.findAll(new Specification<Packages>() {
            @Override
            public Predicate toPredicate(Root<Packages> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (brandId != null) {
                    Predicate pre = cb.equal(root.get("brand").get("id").as(Integer.class), brandId);
                    predicateList.add(pre);
                }

                if (StringUtils.isNotBlank(name)) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
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
    public List<Packageproducts> findListByPackageId(Integer packageId) {
        List<Packageproducts> list = packagesDao.findListByPackageId(packageId);

        for (Packageproducts packageProduct : list) {
            packageProduct.getProduct().setColors(packageProduct.getColors());
            packageProduct.getProduct().setSizes(packageProduct.getSizes());
            packageProduct.getProduct().setMaterials(packageProduct.getMaterials());
        }

        return list;
    }

    @Override
    public Page<Packages> page(final String name, final Integer brandId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Packages> page = packagesDao.findAll(new Specification<Packages>() {
            @Override
            public Predicate toPredicate(Root<Packages> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (name != null) {
                    Predicate pre = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(pre);
                }

                if (brandId != null) {
                    Predicate pre = cb.equal(root.get("brand").get("id").as(Integer.class), brandId);
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
    public List<Packages> iFindListByBrand(Integer packageId, Integer brandId) {
        return packagesDao.iFindListByBrand(packageId, brandId);
    }

    @Override
    public List<Packages> findListOrderByCreateTimeDesc() {
        return packagesDao.findListOrderByCreateTimeDesc();
    }

    @Override
    @Transactional
    public void deleteById(HttpServletRequest request, Integer id) {
        operatisService.addOperatisInfo(request, "删除商品套餐 " + packagesDao.findOne(id).getName());

        deleteById(id);
    }

}