package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustompackagesDao;
import com.sixmac.dao.PackageproductsDao;
import com.sixmac.entity.*;
import com.sixmac.entity.vo.PackageVo;
import com.sixmac.service.CustompackagesService;
import com.sixmac.service.ImageService;
import com.sixmac.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:09.
 */
@Service
public class CustompackagesServiceImpl implements CustompackagesService {

    @Autowired
    private CustompackagesDao custompackagesDao;

    @Autowired
    private PackageproductsDao packageProductsDao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LabelService labelService;

    @Override
    public List<Custompackages> findAll() {
        return custompackagesDao.findAll();
    }

    @Override
    public Page<Custompackages> find(int pageNum, int pageSize) {
        return custompackagesDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Custompackages> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Custompackages getById(int id) {
        return custompackagesDao.findOne(id);
    }

    @Override
    @Transactional
    public Custompackages deleteById(int id) {
        Custompackages custompackages = getById(id);

        // 删除户型套餐之前，先删除商品与套餐的关联信息
        List<Packageproducts> list = packageProductsDao.findListByPackageId(id, Constant.PACKAGE_TYPE_CUSTOM);

        for (Packageproducts packageProduct : list) {
            packageProductsDao.delete(packageProduct.getId());
        }

        custompackagesDao.delete(custompackages);
        return custompackages;
    }

    @Override
    public Custompackages create(Custompackages custompackages) {
        return custompackagesDao.save(custompackages);
    }

    @Override
    public Custompackages update(Custompackages custompackages) {
        return custompackagesDao.save(custompackages);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<PackageVo> findListByCustominfoId(Integer custominfoId, Integer areaId) {
        PackageVo packageVo = null;
        List<PackageVo> packageVoList = new ArrayList<PackageVo>();
        List<Products> productList = new ArrayList<Products>();
        Image image = null;

        // 根据户型id查询户型套餐列表
        List<Custompackages> list = null;

        if (null == areaId) {
            list = custompackagesDao.findListByCustominfoId(custominfoId);
        } else {
            list = custompackagesDao.findListByCustominfoId(custominfoId, areaId);
        }

        Double price = 0.0;

        for (Custompackages customPackage : list) {
            packageVo = new PackageVo();
            packageVo.setId(customPackage.getId());
            packageVo.setName(customPackage.getName());
            image = imageService.getById(customPackage.getCoverId());
            image.setLabelList(labelService.findListByParams(image.getId(), Constant.LABEL_CUSTOMPACKAGE));
            packageVo.setCover(image);

            for (Label label : image.getLabelList()) {
                price += Double.parseDouble(label.getProduct().getPrice());
            }

            packageVo.setPrice(price);

            packageVoList.add(packageVo);

            price = 0.0;
        }

        return packageVoList;
    }

    @Override
    @Transactional
    public void deleteAllInfoByCustomInfoId(Integer customInfoId) {
        List<Custompackages> list = custompackagesDao.findListByCustominfoId(customInfoId);

        for (Custompackages customPackage : list) {
            deleteById(customPackage.getId());
        }
    }

    @Override
    public List<Custompackages> findListByCustomInfoId(Integer customInfoId) {
        return custompackagesDao.findListByCustominfoId(customInfoId);
    }
}