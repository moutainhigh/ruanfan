package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustomDao;
import com.sixmac.entity.Custom;
import com.sixmac.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:07.
 */
@Service
public class CustomServiceImpl implements CustomService {

    @Autowired
    private CustomDao customDao;

    @Override
    public List<Custom> findAll() {
        return customDao.findAll();
    }

    @Override
    public Page<Custom> find(int pageNum, int pageSize) {
        return customDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Custom> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Custom getById(int id) {
        return customDao.findOne(id);
    }

    @Override
    public Custom deleteById(int id) {
        Custom custom = getById(id);
        customDao.delete(custom);
        return custom;
    }

    @Override
    public Custom create(Custom custom) {
        return customDao.save(custom);
    }

    @Override
    public Custom update(Custom custom) {
        return customDao.save(custom);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Custom findOneByParams(String name) {
        return customDao.findOneByParams(name);
    }

    @Override
    public Custom findOneByHot() {
        return customDao.findOneByHot();
    }
}