package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustompackagesDao;
import com.sixmac.dao.ImageDao;
import com.sixmac.dao.PackageproductsDao;
import com.sixmac.entity.Custompackages;
import com.sixmac.entity.Packageproducts;
import com.sixmac.entity.Products;
import com.sixmac.entity.vo.PackageVo;
import com.sixmac.service.CustompackagesService;
import com.sixmac.service.ProductsService;
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
    private ProductsService productService;

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
    public Custompackages deleteById(int id) {
        Custompackages custompackages = getById(id);
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
    public List<PackageVo> findListByCustominfoId(Integer custominfoId) {
        List<Packageproducts> packageproductsList = null;
        PackageVo packageVo = null;
        List<PackageVo> packageVoList = new ArrayList<PackageVo>();
        List<Products> productList = new ArrayList<Products>();

        // 根据户型id查询户型套餐列表
        List<Custompackages> list = custompackagesDao.findListByCustominfoId(custominfoId);

        for (Custompackages customPackage : list) {
            packageVo = new PackageVo();
            packageVo.setId(customPackage.getId());
            packageVo.setName(customPackage.getName());

            // 根据套餐id和套餐类型查询套餐商品集合
            packageproductsList = packageProductsDao.findListByPackageId(customPackage.getId(), Constant.PACKAGE_TYPE_CUSTOM);

            // 将套餐商品里面的商品循环读取，放入到缓存商品集合中
            for (Packageproducts packageProduct : packageproductsList) {
                productList.add(packageProduct.getProduct());
            }

            packageVo.setProductsList(productList);

            packageVoList.add(packageVo);
        }

        return packageVoList;
    }
}