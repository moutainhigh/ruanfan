package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.AfflatusDao;
import com.sixmac.entity.Afflatus;
import com.sixmac.service.AfflatusService;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/4 0004 下午 5:10.
 */
@Service
public class AfflatusServiceImpl implements AfflatusService {

    @Autowired
    private AfflatusDao afflatusDao;

    @Override
    public List<Afflatus> findAll() {
        return afflatusDao.findAll();
    }

    @Override
    public Page<Afflatus> find(int pageNum, int pageSize) {
        return afflatusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Afflatus> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Afflatus getById(int id) {
        return afflatusDao.findOne(id);
    }

    @Override
    public Afflatus deleteById(int id) {
        Afflatus afflatus = getById(id);
        afflatusDao.delete(afflatus);
        return afflatus;
    }

    @Override
    public Afflatus create(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    public Afflatus update(Afflatus afflatus) {
        return afflatusDao.save(afflatus);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Afflatus> iPage(String type, Integer styleId, Integer areaId, Integer pageNum, Integer pageSize) {
        Page<Afflatus> page = afflatusDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
        return page;
    }
}