package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.CustompackagesDao;
import com.sixmac.entity.Custompackages;
import com.sixmac.service.CustompackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008 下午 3:09.
 */
@Service
public class CustompackagesServiceImpl implements CustompackagesService {

    @Autowired
    private CustompackagesDao custompackagesDao;

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
}