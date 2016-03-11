package com.sixmac.service.impl;

import com.sixmac.core.Constant;
import com.sixmac.dao.UsersotherDao;
import com.sixmac.entity.Usersother;
import com.sixmac.service.UsersotherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11 0011 上午 10:55.
 */
@Service
public class UsersotherServiceImpl implements UsersotherService {

    @Autowired
    private UsersotherDao usersotherDao;

    @Override
    public List<Usersother> findAll() {
        return usersotherDao.findAll();
    }

    @Override
    public Page<Usersother> find(int pageNum, int pageSize) {
        return usersotherDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Usersother> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Usersother getById(int id) {
        return usersotherDao.findOne(id);
    }

    @Override
    public Usersother deleteById(int id) {
        Usersother usersother = getById(id);
        usersotherDao.delete(usersother);
        return usersother;
    }

    @Override
    public Usersother create(Usersother usersother) {
        return usersotherDao.save(usersother);
    }

    @Override
    public Usersother update(Usersother usersother) {
        return usersotherDao.save(usersother);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }
}