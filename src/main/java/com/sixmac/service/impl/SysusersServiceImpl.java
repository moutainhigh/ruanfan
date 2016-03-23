package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.SysusersDao;
import com.sixmac.entity.Sysusers;
import com.sixmac.service.SysusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023 下午 12:02.
 */
@Service
public class SysusersServiceImpl implements SysusersService {

    @Autowired
    private SysusersDao sysusersDao;

    @Override
    public List<Sysusers> findAll() {
        return sysusersDao.findAll();
    }

    @Override
    public Page<Sysusers> find(int pageNum, int pageSize) {
        return sysusersDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Sysusers> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Sysusers getById(int id) {
        return sysusersDao.findOne(id);
    }

    @Override
    public Sysusers deleteById(int id) {
        Sysusers sysusers = getById(id);
        sysusersDao.delete(sysusers);
        return sysusers;
    }

    @Override
    public Sysusers create(Sysusers sysusers) {
        return sysusersDao.save(sysusers);
    }

    @Override
    public Sysusers update(Sysusers sysusers) {
        return sysusersDao.save(sysusers);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}