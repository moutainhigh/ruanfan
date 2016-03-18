package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.PropertyinfoDao;
import com.sixmac.entity.Propertyinfo;
import com.sixmac.service.PropertyinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018 上午 10:25.
 */
@Service
public class PropertyinfoServiceImpl implements PropertyinfoService {

    @Autowired
    private PropertyinfoDao propertyinfoDao;

    @Override
    public List<Propertyinfo> findAll() {
        return propertyinfoDao.findAll();
    }

    @Override
    public Page<Propertyinfo> find(int pageNum, int pageSize) {
        return propertyinfoDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Propertyinfo> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Propertyinfo getById(int id) {
        return propertyinfoDao.findOne(id);
    }

    @Override
    public Propertyinfo deleteById(int id) {
        Propertyinfo propertyinfo = getById(id);
        propertyinfoDao.delete(propertyinfo);
        return propertyinfo;
    }

    @Override
    public Propertyinfo create(Propertyinfo propertyinfo) {
        return propertyinfoDao.save(propertyinfo);
    }

    @Override
    public Propertyinfo update(Propertyinfo propertyinfo) {
        return propertyinfoDao.save(propertyinfo);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional
    public void clearInfoByPropertyId(Integer propertyId) {
        List<Propertyinfo> list = propertyinfoDao.iFindListByPropertyId(propertyId);
        for (Propertyinfo propertyInfo : list) {
            propertyinfoDao.delete(propertyInfo.getId());
        }
    }

    @Override
    public List<Propertyinfo> findListByPropertyId(Integer propertyId) {
        return propertyinfoDao.iFindListByPropertyId(propertyId);
    }
}