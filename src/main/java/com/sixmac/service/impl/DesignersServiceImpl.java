package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.DesignersDao;
import com.sixmac.entity.Designers;
import com.sixmac.service.DesignersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007 下午 1:52.
 */
@Service
public class DesignersServiceImpl implements DesignersService {

    @Autowired
    private DesignersDao designersDao;

    @Override
    public List<Designers> findAll() {
        return designersDao.findAll();
    }

    @Override
    public Page<Designers> find(int pageNum, int pageSize) {
        return designersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Designers> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Designers getById(int id) {
        return designersDao.findOne(id);
    }

    @Override
    public Designers deleteById(int id) {
        Designers designers = getById(id);
        designersDao.delete(designers);
        return designers;
    }

    @Override
    public Designers create(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    public Designers update(Designers designers) {
        return designersDao.save(designers);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Designers> iPage(Integer type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize) {
        return null;
    }
}